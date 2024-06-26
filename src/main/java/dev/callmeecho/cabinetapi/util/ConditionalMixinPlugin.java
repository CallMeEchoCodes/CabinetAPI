package dev.callmeecho.cabinetapi.util;

import dev.callmeecho.cabinetapi.CabinetAPI;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.VersionParsingException;
import net.fabricmc.loader.api.metadata.version.VersionPredicate;
import net.fabricmc.loader.impl.util.version.VersionPredicateParser;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.service.MixinService;
import org.spongepowered.asm.util.Annotations;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConditionalMixinPlugin implements IMixinConfigPlugin {
    private final Map<String, Boolean> cache = new HashMap<>();
    private String mixinPackage;

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (this.mixinPackage != null && !mixinClassName.startsWith(this.mixinPackage)) return true;
        synchronized (cache) {
            Boolean result = cache.get(mixinClassName);
            if (result != null) return result;

            ClassNode classNode;
            try { classNode = MixinService.getService().getBytecodeProvider().getClassNode(mixinClassName); }
            catch (ClassNotFoundException | IOException e) { throw new RuntimeException(e); }

            AnnotationNode modCondition = Annotations.getVisible(classNode, ModCondition.class);
            AnnotationNode minecraftCondition = Annotations.getVisible(classNode, MinecraftCondition.class);
            AnnotationNode devEnvironmentCondition = Annotations.getVisible(classNode, DevEnvironmentCondition.class);

            result = true;
            if (modCondition != null)
                result = checkModCondition(modCondition);
            if (minecraftCondition != null)
                result &= checkMinecraftCondition(minecraftCondition);
            if (devEnvironmentCondition != null)
                result &= CabinetAPI.DEBUG;

            cache.put(mixinClassName, result);
            return result;
        }
    }

    private boolean checkModCondition(AnnotationNode annotationNode) {
        String modId = ReflectionHelper.getAnnotationValue(annotationNode, "value", "");
        String versionPredicate = ReflectionHelper.getAnnotationValue(annotationNode, "versionPredicate", "");
        boolean negated = ReflectionHelper.getAnnotationValue(annotationNode, "negated", false);
        if (modId.isEmpty()) throw new IllegalArgumentException("ModCondition annotation must have a value");

        return negated ^ isModLoaded(modId, versionPredicate);
    }

    private boolean checkMinecraftCondition(AnnotationNode annotationNode) {
        String versionPredicate = ReflectionHelper.getAnnotationValue(annotationNode, "value", "");
        boolean negated = ReflectionHelper.getAnnotationValue(annotationNode, "negated", false);

        return negated ^ isModLoaded("minecraft", versionPredicate);
    }

    private boolean isModLoaded(String modId, String versionPredicate) {
        ModContainer modContainer = FabricLoader.getInstance().getModContainer(modId).orElse(null);
        if (modContainer == null) return false;

        if (!versionPredicate.isEmpty()) {
            VersionPredicate predicate;
            try { predicate = VersionPredicateParser.parse(versionPredicate); }
            catch (VersionParsingException e) { throw new RuntimeException(e); }

            return predicate.test(modContainer.getMetadata().getVersion());
        }

        return true;
    }


    @Override
    public void onLoad(String mixinPackage) {
        this.mixinPackage = mixinPackage;
    }

    @Override
    public String getRefMapperConfig() { return null; }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) { }

    @Override
    public List<String> getMixins() { return null; }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) { }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) { }
}
