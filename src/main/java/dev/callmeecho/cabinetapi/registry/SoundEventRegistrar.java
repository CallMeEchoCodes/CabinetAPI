package dev.callmeecho.cabinetapi.registry;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;

public interface SoundEventRegistrar extends Registrar<SoundEvent> {
    @Override
    default Registry<SoundEvent> getRegistry() {
        return Registries.SOUND_EVENT;
    }
}
