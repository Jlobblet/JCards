package jcards.powers

import com.badlogic.gdx.graphics.Texture
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.localization.PowerStrings
import com.megacrit.cardcrawl.powers.AbstractPower

class Deenergized(owner: AbstractCreature?, energyAmt: Int) : AbstractPower() {
    companion object {
        const val POWER_ID: String = "JCards:Deenergized"
        val cardStrings: PowerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID)
        val NAME: String = cardStrings.NAME
        val DESCRIPTIONS: Array<String> = cardStrings.DESCRIPTIONS
        const val IMG_PATH: String = "JCardsResources/images/powers/Deenergized.png"
    }

    init {
        this.ID = POWER_ID
        this.name = NAME
        this.owner = owner
        this.amount = energyAmt
        this.img = Texture(IMG_PATH)
        this.img.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
        this.type = PowerType.DEBUFF

        clampAmount()
        this.updateDescription()
    }

    override fun onEnergyRecharge() {
        this.flash()
        AbstractDungeon.player.loseEnergy(this.amount)
        this.addToBot(RemoveSpecificPowerAction(this.owner, this.owner, ID))
    }

    override fun updateDescription() {
        val suffix = DESCRIPTIONS[if (this.amount == 1) { 1 } else { 2 }]
        this.description = DESCRIPTIONS[0] + this.amount + suffix
    }

    override fun stackPower(stackAmount: Int) {
        super.stackPower(stackAmount)
        clampAmount()
    }

    private fun clampAmount() {
        if (this.amount >= 999) {
            this.amount = 999
        }
    }
}
