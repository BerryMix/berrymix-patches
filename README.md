# 👋🧩 Morphe Patches template

Template repository for Morphe Patches.

## ❓ About

Patches for apps I like.

<!-- TODO: Update this about section with a brief introduction/summary about this repo and what it offers. -->

### How to use these patches

Click the button below, or manually add this repo url to Morphe Manager: https://github.com/BerryMix/berrymix-patches

<a href="https://morphe.software/add-source?github=BerryMix/berrymix-patches">
  <img src=".github/assets/add-to-morphe.svg" alt="Add to Morphe" height="48">
</a>

## 🩹 Patches list

<!-- PATCHES_START -->
> **[v1.0.0](https://github.com/BerryMix/berrymix-patches/releases/tag/v1.0.0)**&nbsp;&nbsp;&middot;&nbsp;&nbsp;`main`&nbsp;&nbsp;&middot;&nbsp;&nbsp;**1 patch** across **1 app**&nbsp;&nbsp;&middot;&nbsp;&nbsp;[Full details](PATCHES.md)

| # | App | Patches | Version | Package |
|---|---|---|---|---|
| 1 | [**Bluetooth Codec Changer**](PATCHES.md#bluetooth-codec-changer-comamrgbluetooth_codec_converter) | 1 | `2.6.1` | [`com.amrg.bluetooth_codec_converter`](https://play.google.com/store/apps/details?id=com.amrg.bluetooth_codec_converter) |
<!-- PATCHES_END -->

### 🛠️ Building locally

- Run `./gradlew buildAndroid`
- The built patches .mpp file is found in `patches/build/libs/patches-*.mpp`
- Patch the mpp file using [Morphe-Desktop](https://github.com/MorpheApp/morphe-desktop)
  like any other patch bundle.

See the [Morphe documentation](https://github.com/MorpheApp/morphe-documentation) for more information.

## 📜 License

UserXYZ Patches are licensed under the [GNU General Public License v3.0](LICENSE)
