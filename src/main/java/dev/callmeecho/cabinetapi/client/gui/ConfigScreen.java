package dev.callmeecho.cabinetapi.client.gui;

import dev.callmeecho.cabinetapi.CabinetAPI;
import dev.callmeecho.cabinetapi.client.gui.widget.*;
import dev.callmeecho.cabinetapi.config.Config;
import dev.callmeecho.cabinetapi.config.NestedConfig;
import dev.callmeecho.cabinetapi.config.annotations.Range;
import dev.callmeecho.cabinetapi.util.ReflectionHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Environment(EnvType.CLIENT)
public class ConfigScreen extends Screen {
    protected final Screen parent;
    private final Config configClass;
    protected OptionsScrollableWidget scrollableWidget;

    public ConfigScreen(Config configClass, Screen parent) {
        super(Text.translatable("config." + configClass.getName() + ".title"));
        this.configClass = configClass;
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();

        this.scrollableWidget = new OptionsScrollableWidget(this.client, this.width, this.height - 64, 32, 25);

        List<CabinetWidget> options = new ArrayList<>();
        for (Field field : configClass.getClass().getDeclaredFields()) {
            addOption(options, field);
        }
        this.scrollableWidget.addOptions(Arrays.copyOf(options.toArray(), options.size(), CabinetWidget[].class));

        this.addDrawableChild(this.scrollableWidget);
        this.addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, button -> close())
                .dimensions(this.width / 2 - 100, this.height - 27, 200, 20)
                .build()
        );
    }

    @Override
    public void close() {
        save();

        if (this.client == null) return;
        this.client.setScreen(this.parent);
    }

    public void save() {
        if (configClass instanceof NestedConfig && this.parent instanceof ConfigScreen) {
            ((ConfigScreen) this.parent).save();
            return;
        }
        configClass.save();
    }

    private void addNestedClass(List<CabinetWidget> options, Field field, NestedConfig value) {
        ConfigScreen nestedScreen = new ConfigScreen(value, this);

        for (Field nestedField : value.getClass().getDeclaredFields()) {
            if (nestedField.getType().isAssignableFrom(NestedConfig.class)) {
                NestedConfig nestedValue = ReflectionHelper.getFieldValue(value.getClass().getDeclaredFields(), nestedField);
                addNestedClass(options, nestedField, nestedValue);
            }
        }

        options.add(new CabinetButtonWidget(
                this.width / 2 - 100,
                0,
                200,
                20,
                Text.translatable("config." + value.getName() + ".title"),
                button -> {
                    save();

                    if (this.client == null) return;
                    this.client.setScreen(nestedScreen);
                },
                null
        ));
    }

    private void addOption(List<CabinetWidget> options, Field field) {
        Object value = ReflectionHelper.getFieldValue(configClass, field);
        switch (value) {
            case String ignored -> options.add(new TextBoxWidget(
                    configClass.getTranslationKey(field),
                    () -> ReflectionHelper.getFieldValue(configClass, field),
                    newValue -> ReflectionHelper.setFieldValue(configClass, field, newValue)
            ));
            case Boolean ignored -> options.add(new BooleanButtonWidget(
                    configClass.getTranslationKey(field),
                    () -> ReflectionHelper.getFieldValue(configClass, field),
                    newValue -> ReflectionHelper.setFieldValue(configClass, field, newValue)
            ));
            case Integer ignored -> {
                int min = 0;
                int max = 100;
                if (field.isAnnotationPresent(Range.class)) {
                    Range range = field.getAnnotation(Range.class);
                    min = (int) range.min();
                    max = (int) range.max();
                }

                options.add(new IntSliderWidget(
                        configClass.getTranslationKey(field),
                        min,
                        max,
                        () -> ReflectionHelper.getFieldValue(configClass, field),
                        newValue -> ReflectionHelper.setFieldValue(configClass, field, newValue)
                ));
            }
            case Float ignored -> {
                float min = 0;
                float max = 100;
                if (field.isAnnotationPresent(Range.class)) {
                    Range range = field.getAnnotation(Range.class);
                    min = (float) range.min();
                    max = (float) range.max();
                }

                options.add(new FloatSliderWidget(
                        configClass.getTranslationKey(field),
                        min,
                        max,
                        () -> ReflectionHelper.getFieldValue(configClass, field),
                        newValue -> ReflectionHelper.setFieldValue(configClass, field, newValue)
                ));
            }
            case Double ignored -> {
                double min = 0;
                double max = 100;
                if (field.isAnnotationPresent(Range.class)) {
                    Range range = field.getAnnotation(Range.class);
                    min = range.min();
                    max = range.max();
                }

                options.add(new DoubleSliderWidget(
                        configClass.getTranslationKey(field),
                        min,
                        max,
                        () -> ReflectionHelper.getFieldValue(configClass, field),
                        newValue -> ReflectionHelper.setFieldValue(configClass, field, newValue)
                ));
            }

            case Enum<?> ignored -> {
                EnumButtonWidget enumButtonWidget = new EnumButtonWidget(
                        configClass.getTranslationKey(field),
                        () -> ReflectionHelper.getFieldValue(configClass, field),
                        newValue -> ReflectionHelper.setFieldValue(configClass, field, newValue),
                        (Enum<?>) value
                );
                options.add(enumButtonWidget);
            }

            case NestedConfig nestedValue -> this.addNestedClass(options, field, nestedValue);

            case null, default -> {
                if (CabinetAPI.DEBUG)
                    CabinetAPI.LOGGER.warn("Unsupported config type: {}", field.getType().getName());
            }
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);

        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);
    }
}
