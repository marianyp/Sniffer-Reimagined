package dev.mariany.snifferreimagined.mixin;

import dev.mariany.snifferreimagined.client.render.entity.state.SnifferVariantRenderState;
import net.minecraft.client.render.entity.state.SnifferEntityRenderState;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(SnifferEntityRenderState.class)
public class SnifferEntityRenderStateMixin implements SnifferVariantRenderState {
    @Unique
    public Identifier texture = Identifier.ofVanilla("textures/entity/sniffer/sniffer.png");

    @Override
    public Identifier snifferReimagined$getTexture() {
        return this.texture;
    }

    @Override
    public void snifferReimagined$setTexture(Identifier texture) {
        this.texture = texture;
    }
}
