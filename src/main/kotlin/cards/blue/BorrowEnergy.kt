package jcards.cards.blue

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.defect.ChannelAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.localization.CardStrings
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.orbs.Plasma
import jcards.powers.Deenergized

class BorrowEnergy : CustomCard(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET) {
    companion object {
        const val ID: String = "JCards:Borrow Energy"
        private val cardStrings: CardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME: String = cardStrings.NAME
        val DESCRIPTION: String = cardStrings.DESCRIPTION
        const val IMG_PATH = "JCardsResources/images/cards/Borrow Energy.png"
        const val COST = 1
        const val UPGRADED_COST = 0
        val TYPE = CardType.SKILL
        val COLOR = CardColor.BLUE
        val RARITY = CardRarity.UNCOMMON
        val TARGET = CardTarget.SELF
    }

    init {
        this.baseMagicNumber = 1
        this.magicNumber = baseMagicNumber
    }

    override fun upgrade() {
        if (this.upgraded) return
        this.upgradeName()
        this.upgradeBaseCost(UPGRADED_COST)
    }

    override fun use(p: AbstractPlayer?, m: AbstractMonster?) {
        val orb = Plasma()
        this.addToBot(ChannelAction(orb))
        this.addToBot(ApplyPowerAction(p, p, Deenergized(p, 1), 1))
    }
}