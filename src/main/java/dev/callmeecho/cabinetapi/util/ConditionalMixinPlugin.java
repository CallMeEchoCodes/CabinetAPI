package dev.callmeecho.cabinetapi.util;

import dev.callmeecho.cabinetapi.CabinetAPI;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.VersionParsingException;
import net.fabricmc.loader.api.metadata.version.VersionPredicate;
import net.fabricmc.loader.impl.util.version.VersionPredicateParser;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.service.MixinService;
import org.spongepowered.asm.util.Annotations;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class ConditionalMixinPlugin implements IMixinConfigPlugin {
    private final Map<String, Boolean> cache = new HashMap<>();

    @Override
    public void onLoad(String mixinPackage) { }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        synchronized (this.cache) {
            Boolean result = this.cache.get(mixinClassName);
            if (result != null) return result;

            ClassNode mixinClass;
            try {
                mixinClass = MixinService.getService().getBytecodeProvider().getClassNode(mixinClassName);
            } catch (IOException | ClassNotFoundException e) {
                return false;
            }

            AnnotationNode annotation = Annotations.getVisible(mixinClass, Condition.class);
            if (annotation == null) {
                this.cache.put(mixinClassName, true);
                return true;
            }

            String modid = Annotations.getValue(annotation, "value");
            String versionPredicateString = Annotations.getValue(annotation, "versionPredicate");
            if (modid == null) {
                CabinetAPI.LOGGER.error("Missing modid in @Condition annotation on " + mixinClassName);
                this.cache.put(mixinClassName, false);
                return false;
            }

            if (modid.isEmpty()) {
                this.cache.put(mixinClassName, true);
                return true;
            }

            if (versionPredicateString == null) versionPredicateString = "";
            if (!versionPredicateString.isEmpty()) {
                try {
                    VersionPredicate versionPredicate = VersionPredicateParser.parse(versionPredicateString);
                    String modVersion = FabricLoader.getInstance().getModContainer(modid).map(container -> container.getMetadata().getVersion().getFriendlyString()).orElse(null);

                    if (modVersion == null || !versionPredicate.test(Version.parse(modVersion))) {
                        this.cache.put(mixinClassName, false);
                        return false;
                    }
                } catch (VersionParsingException e) {
                    CabinetAPI.LOGGER.error("Failed to parse version predicate: " + versionPredicateString);
                    this.cache.put(mixinClassName, false);
                    return false;
                }
            }

            boolean resultValue = FabricLoader.getInstance().isModLoaded(modid);
            this.cache.put(mixinClassName, resultValue);
            return resultValue;
        }
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}
