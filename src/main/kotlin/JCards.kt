package jcards

import basemod.BaseMod
import basemod.ModLabel
import basemod.ModPanel
import basemod.interfaces.EditCardsSubscriber
import basemod.interfaces.EditStringsSubscriber
import basemod.interfaces.OnPowersModifiedSubscriber
import basemod.interfaces.PostInitializeSubscriber
import com.badlogic.gdx.graphics.Texture
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer
import com.megacrit.cardcrawl.localization.CardStrings
import com.megacrit.cardcrawl.localization.PowerStrings
import com.megacrit.cardcrawl.unlock.UnlockTracker
import jcards.cards.blue.BorrowEnergy
import jcards.powers.Deenergized
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@SpireInitializer
class JCards : EditCardsSubscriber, EditStringsSubscriber, OnPowersModifiedSubscriber, PostInitializeSubscriber {
    private companion object {
        const val MOD_NAME: String = "JCards"
        const val AUTHOR: String = "Jlobblet"
        const val DESCRIPTION: String = "Adds some cards."
        const val BADGE_PATH: String = "JCardsResources/images/badge.png"
        val logger: Logger = LogManager.getLogger(JCards::class.qualifiedName)
        private lateinit var instance: JCards

        @JvmStatic
        @SuppressWarnings("unused")
        fun initialize() {
            instance = JCards()
        }
    }

    init {
        logger.info("Subscribing to BaseMod hooks...")
        BaseMod.subscribe(this)
        logger.info("...done")
    }

    override fun receiveEditCards() {
        logger.info("Adding cards...")
        BaseMod.addCard(BorrowEnergy())
        UnlockTracker.unlockCard(BorrowEnergy.ID)
        logger.info("...done")
    }

    override fun receiveEditStrings() {
        logger.info("Editing strings...")
        BaseMod.loadCustomStringsFile(CardStrings::class.java, "${MOD_NAME}Resources/localization/eng/JCards-Card-Strings.json")
        BaseMod.loadCustomStringsFile(PowerStrings::class.java, "${MOD_NAME}Resources/localization/eng/JCards-Power-Strings.json")
        logger.info("...done")
    }

    override fun receivePowersModified() {
        logger.info("Adding powers...")
        BaseMod.addPower(Deenergized::class.java, Deenergized.POWER_ID)
        logger.info("...done")
    }

    override fun receivePostInitialize() {
        logger.info("Loading badge...")

        val badgeTexture = Texture(BADGE_PATH)
        badgeTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)

        val settingsPanel = ModPanel()
        settingsPanel.addUIElement(ModLabel("JCards", 0.0F, 0.0F, settingsPanel) { })

        BaseMod.registerModBadge(badgeTexture, MOD_NAME, AUTHOR, DESCRIPTION, settingsPanel)

        logger.info("...done")
    }
}
