package com.example.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class DeathMessageMixin {

    // method_6068 is the Intermediary name for getDeathMessage in 1.21
    @Inject(method = "method_6068", at = @At("RETURN"), cancellable = true)
    private void changeDeathMessage(CallbackInfoReturnable<Text> cir) {
        try {
            LivingEntity victim = (LivingEntity) (Object) this;
            DamageSource source = victim.getRecentDamageSource();
            
            if (source != null && source.getAttacker() != null) {
                String victimName = victim.getName().getString();
                String attackerName = source.getAttacker().getName().getString();
                String suffix = "ed";

                String lowerAttacker = attackerName.toLowerCase();
                if (lowerAttacker.endsWith("e")) {
                    suffix = "d";
                } else if (lowerAttacker.endsWith("y")) {
                    attackerName = attackerName.substring(0, attackerName.length() - 1);
                    suffix = "ied";
                }

                String newMessage = victimName + " Has Been " + attackerName + suffix;
                cir.setReturnValue(Text.literal(newMessage));
            }
        } catch (Exception e) {
            // If anything fails, let the original death message show instead of crashing
        }
    }
}
