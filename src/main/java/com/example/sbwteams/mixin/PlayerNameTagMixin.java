package com.example.sbwteams.mixin;

import com.example.sbwteams.team.Team;
import com.example.sbwteams.team.TeamManager;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class PlayerNameTagMixin {

    @Inject(method = "isInvisibleTo", at = @At("RETURN"), cancellable = true)
    private void forceNameVisibility(Player player, CallbackInfoReturnable<Boolean> cir) {
        if (player instanceof LocalPlayer local && this instanceof Player target) {
            Team myTeam = TeamManager.getTeam(local);
            Team targetTeam = TeamManager.getTeam(target);
            if (myTeam != null && myTeam.equals(targetTeam)) {
                cir.setReturnValue(false); // Всегда видимы для своей команды
            }
        }
    }
}
