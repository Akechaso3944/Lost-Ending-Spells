package com.akechaso.LOSTENDINGMOD.main.item.weapons;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class ScytheItem extends SwordItem {

    private final float range;
    private final float aoeMultiplier;

    public ScytheItem(Tier tier, int attackDamage, float attackSpeed,
                      float range, float aoeMultiplier, Properties properties) {
        super(tier, attackDamage, attackSpeed, properties);
        this.range = range;
        this.aoeMultiplier = aoeMultiplier;
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        boolean result = super.hurtEnemy(stack, target, attacker);

        if (!(attacker instanceof Player player)) return result;
        if (attacker.level().isClientSide) return result;

        // メイン攻撃分の耐久消費
        stack.hurtAndBreak(1, attacker, e ->
                e.broadcastBreakEvent(player.getUsedItemHand())
        );

        // 範囲攻撃
        AABB box = target.getBoundingBox().inflate(range, 0.5D, range);
        List<LivingEntity> entities = attacker.level().getEntitiesOfClass(
                LivingEntity.class,
                box,
                e -> e != attacker && e != target && e.isAlive()
        );

        float baseDamage = this.getDamage();

        for (LivingEntity entity : entities) {
            entity.hurt(
                    attacker.level().damageSources().playerAttack(player),
                    baseDamage * aoeMultiplier
            );

            // 範囲攻撃ヒットごとに耐久消費（軽め）
            stack.hurtAndBreak(1, attacker, e ->
                    e.broadcastBreakEvent(player.getUsedItemHand())
            );
        }

        return result;
    }
}

