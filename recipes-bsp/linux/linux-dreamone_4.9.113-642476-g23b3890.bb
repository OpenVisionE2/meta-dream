DEPENDS = "libgcc"
RDEPENDS_${PN} += "flash-scripts"
PROVIDES = "linux-dreambox"

PACKAGE_ARCH = "${MACHINE_ARCH}"

require recipes-bsp/linux/linux-dreambox-one.inc

SRC_URI = "https://dreamboxupdate.com/download/opendreambox/linux-meson64/linux-meson64-v${PV}.tar.xz \
    file://${OPENVISION_BASE}/meta-openvision/recipes-linux/kernel-patches/kernel-add-support-for-gcc9.patch \
    "

SRC_URI[md5sum] = "d5770c1cf86b093cb17fa6dafce03ff9"
SRC_URI[sha256sum] = "258e542be44bef8a36fe63fc963e1c8e14a6e549bafd4e1cb44817aedea9a7d8"

KERNEL_CC += "${TOOLCHAIN_OPTIONS}"
KERNEL_LD += "${TOOLCHAIN_OPTIONS}"

S = "${WORKDIR}/linux-meson64-v${PV}"

CMDLINE = "${@kernel_console(d)} root=/dev/mmcblk0p7 rootwait rootfstype=ext4 no_console_suspend"

COMPATIBLE_MACHINE = "^(dreamone)$"

DEFCONFIG = "meson64"

LINUX_VERSION = "4.9"
KERNEL_DEVICETREE = "dreamone.dtb"
KERNEL_IMAGETYPES = "Image.gz"

export KCFLAGS = "-Wno-error=misleading-indentation \
                  -Wno-error=parentheses \
                  -Wno-error=shift-overflow \
                  -Wno-error=unused-const-variable \
                  -Wno-error=array-bounds \
                  -Wno-error=address-of-packed-member \
                  -Wno-error=maybe-uninitialized \
                  -Wno-error=missing-attributes \
                  -Wno-error=unused-variable \
                  "

do_install_prepend() {
	mv ${B}/arch/arm64/boot/dts/amlogic/dreamone.dtb ${B}/arch/arm64/boot/
}

KERNEL_FLASH_ARGS = "-c '${CMDLINE}'"
