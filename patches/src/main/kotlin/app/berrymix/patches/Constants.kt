package app.berrymix.patches

import app.morphe.patcher.patch.ApkFileType
import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.Compatibility

object Constants {
    val COMPATIBILITY = Compatibility(
        name = "Bluetooth Codec Changer",
        packageName = "com.amrg.bluetooth_codec_converter",
        apkFileType = ApkFileType.XAPK,
        appIconColor = 0x0B172A,
        targets = listOf(
            AppTarget(
                version = "2.6.1"
            )
        )
    )
}
