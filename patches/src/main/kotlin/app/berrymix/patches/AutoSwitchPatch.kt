package app.berrymix.patches

import app.berrymix.patches.Constants.COMPATIBILITY
import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.extensions.InstructionExtensions.replaceInstruction
import app.morphe.patcher.patch.patch
import app.morphe.patcher.util.getReference
import app.morphe.patcher.util.registersUsed
import app.morphe.patcher.util.returnEarly
import com.android.tools.smali.dexlib2.iface.reference.MethodReference

private const val EXTENSION_CLASS = "Lapp/berrymix/extension/AutoSwitchPatch;"

/**
 * Implemented the 4PDA @KOROLyt mod as a patch.
 */
@Suppress("unused")
val autoSwitchPatch = patch(
    name = "Enable auto switch",
    description = "Enable automatic codec switching."
) {
    compatibleWith(COMPATIBILITY)
    extendWith("extensions/extension.mpe")

    execute {
        CheckSignatureFingerprint.method.returnEarly(true)

        val enumMethodCall = PremiumEnumFingerprint.instructionMatches[1].getMethodCalled()
        val premiumField = getEnumFingerprint(enumMethodCall)
            .instructionMatches
            .first()
            .getFieldAccessed()

        getEnumInitFingerprint(premiumField).let {
            it.method.apply {
                val match = it.instructionMatches[1]
                val index = match.index
                val instruction = match.instruction
                val registerC = instruction.registersUsed[0]
                val registerE = instruction.registersUsed[2]
                val reference = instruction.getReference<MethodReference>()!!
                val booleanTrueRegister = it.instructionMatches[3].instruction.registersUsed[1]

                replaceInstruction(
                    index,
                    "invoke-static { v$registerC, v$booleanTrueRegister, v$registerE }, $reference"
                )
            }
        }

        PreferenceGetFingerprint.let {
            it.method.apply {
                val keyRegister = it.instructionMatches[0].instruction.registersUsed[1]
                val valueMatch = it.instructionMatches[1]
                val valueIndex = valueMatch.index
                val valueRegister = valueMatch.instruction.registersUsed[0]

                addInstructions(
                    valueIndex + 1,
                    """
                        invoke-static { v$keyRegister, v$valueRegister }, $EXTENSION_CLASS->getOrDefault(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
                        move-result-object v$valueRegister
                    """
                )
            }
        }

        PreferenceSaveFingerprint.method.addInstructions(
            0,
            """
                invoke-static { p1, p2 }, $EXTENSION_CLASS->getOrDefault(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
                move-result-object p2
            """
        )
    }
}


