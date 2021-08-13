package me.gentworm.storymobs.init;

import me.gentworm.storymobs.StoryMobs;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ParticleInit {

	public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister
			.create(ForgeRegistries.PARTICLE_TYPES, StoryMobs.MODID);
	
	public static final RegistryObject<BasicParticleType> ITEM_RED_SLIME = PARTICLES.register("item_red_slime",
			() -> new BasicParticleType(false));
}