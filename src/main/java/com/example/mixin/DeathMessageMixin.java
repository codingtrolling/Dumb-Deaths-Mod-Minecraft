package com.example.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.CombatTracker;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CombatTracker.class)
public abstract class DeathMessageMixin {

    @Shadow public abstract LivingEntity getEntity();

    @Inject(method = "getDeathMessage", at = @At("RETURN"), cancellable = true)
    private void changeDeathMessage(CallbackInfoReturnable<Text> cir) {
        LivingEntity victim = this.getEntity();
        DamageSource source = victim.getRecentDamageSource();
        
        if (source != null && source.getAttacker() != null) {
            String victimName = victim.getName().getString();
            String attackerName = source.getAttacker().getName().getString();
            String newMessage = victimName + " Has Been " + attackerName + "ed";
            cir.setReturnValue(Text.literal(newMessage));
        }
    }
}
