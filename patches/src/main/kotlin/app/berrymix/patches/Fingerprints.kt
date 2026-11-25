package app.berrymix.patches

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.InstructionLocation.MatchAfterImmediately
import app.morphe.patcher.InstructionLocation.MatchAfterWithin
import app.morphe.patcher.checkCast
import app.morphe.patcher.fieldAccess
import app.morphe.patcher.methodCall
import app.morphe.patcher.opcode
import app.morphe.patcher.string
import app.morphe.patcher.util.proxy.mutableTypes.MutableField
import app.morphe.patcher.util.proxy.mutableTypes.MutableMethod
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode

internal object CheckSignatureFingerprint : Fingerprint(
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.STATIC),
    parameters = listOf("Landroid/content/Context;"),
    returnType = "Z",
    filters = listOf(
        methodCall(
            opcode = Opcode.INVOKE_VIRTUAL,
            smali = "Landroid/content/Context;->getPackageManager()Landroid/content/pm/PackageManager;",
        ),
        methodCall(
            opcode = Opcode.INVOKE_VIRTUAL,
            smali = "Landroid/content/pm/SigningInfo;->hasMultipleSigners()Z",
        ),
        methodCall(
            opcode = Opcode.INVOKE_VIRTUAL,
            smali = "Landroid/content/pm/SigningInfo;->getApkContentsSigners()[Landroid/content/pm/Signature;",
        )
    )
)

internal object PreferenceMapFingerprint : Fingerprint(
    name = "toString",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.FINAL),
    parameters = listOf(),
    returnType = "Ljava/lang/String;",
    strings = listOf("{\n")
)

internal object PreferenceGetFingerprint : Fingerprint(
    classFingerprint = PreferenceMapFingerprint,
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.FINAL),
    parameters = listOf("L"),
    returnType = "Ljava/lang/Object;",
    filters = listOf(
        methodCall(
            opcode = Opcode.INVOKE_VIRTUAL,
            name = "get",
            parameters = listOf("Ljava/lang/Object;"),
            returnType = "Ljava/lang/Object;"
        ),
        opcode(
            opcode = Opcode.MOVE_RESULT_OBJECT,
            location = MatchAfterImmediately()
        ),
        checkCast("[B"),
        methodCall(
            opcode = Opcode.INVOKE_STATIC,
            smali = "Ljava/util/Arrays;->copyOf([BI)[B",
            location = MatchAfterWithin(3)
        )
    )
)

internal object PreferenceSaveFingerprint : Fingerprint(
    classFingerprint = PreferenceMapFingerprint,
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.FINAL),
    parameters = listOf("L", "Ljava/lang/Object;"),
    returnType = "V",
    filters = listOf(
        checkCast("[B"),
        methodCall(
            opcode = Opcode.INVOKE_STATIC,
            smali = "Ljava/util/Arrays;->copyOf([BI)[B",
            location = MatchAfterWithin(3)
        )
    )
)

internal object PremiumEnumFingerprint : Fingerprint(
    definingClass = "Lcom/amrg/bluetooth_codec_converter/ui/base/SharedActivity;",
    parameters = listOf(),
    returnType = "V",
    filters = listOf(
        fieldAccess(
            opcode = Opcode.SGET_OBJECT
        ),
        methodCall(
            opcode = Opcode.INVOKE_VIRTUAL,
            parameters = listOf(),
            returnType = "L",
            location = MatchAfterImmediately()
        ),
        string("pirated")
    )
)

internal fun getEnumFingerprint(premiumClass: MutableMethod) = object : Fingerprint(
    definingClass = premiumClass.definingClass,
    name = premiumClass.name,
    parameters = listOf(),
    returnType = premiumClass.returnType,
    filters = listOf(
        fieldAccess(
            opcode = Opcode.SGET_OBJECT,
            definingClass = "this",
            type = "L"
        )
    )
) {}

internal fun getEnumInitFingerprint(premiumField: MutableField) = object : Fingerprint(
    definingClass = premiumField.definingClass,
    name = "<clinit>",
    filters = listOf(
        string("ProfilesCodecSettings"),
        methodCall(
            opcode = Opcode.INVOKE_STATIC,
            parameters = listOf("L", "Z", "I"),
            returnType = "L"
        ),
        fieldAccess(
            opcode = Opcode.SPUT_OBJECT,
            reference = premiumField,
            location = MatchAfterWithin(3)
        ),
        methodCall(
            opcode = Opcode.INVOKE_STATIC,
            parameters = listOf("L", "Z", "I"),
            returnType = "L",
            location = MatchAfterWithin(3)
        ),
    )
) {}
