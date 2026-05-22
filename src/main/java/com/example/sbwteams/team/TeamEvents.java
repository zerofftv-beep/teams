package com.example.sbwteams.team;

import com.example.sbwteams.vehicle.VehicleOwnership;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class TeamEvents {

    @SubscribeEvent
    public static void onEntityMount(EntityMountEvent event) {
        if (!(event.getEntityMounting() instanceof ServerPlayer player)) return;
        if (!event.isMounting()) return; // Только при посадке

        Entity vehicle = event.getEntityBeingMounted();

        // Если транспорт ещё не привязан — привязываем к текущей группе
        if (VehicleOwnership.getOwnerTeam(vehicle) == null) {
            TeamManager.claimVehicle(vehicle, player);
            player.sendSystemMessage(Component.literal("§aVehicle is now locked to your team."));
            return;
        }

        // Проверяем, может ли игрок сесть
        if (!TeamManager.canMountVehicle(player, vehicle)) {
            event.setCanceled(true);
            player.sendSystemMessage(Component.literal("§cThis vehicle belongs to another team!"));
        }
    }
}
