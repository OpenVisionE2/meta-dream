# dreambox-mips32el-nf.inc

BOX_BRAND = "dreambox"

TARGET_ARCH = "mipsel"
DEFAULTTUNE = "mips32el-nf"

MTD_KERNEL = "mtd2"

KERNEL_FILE = "kernel.bin"
ROOTFS_FILE = "rootfs.bin"

MACHINE_FEATURES_BACKFILL_CONSIDERED = "rtc"
OPTIONAL_BSP_PACKAGES += "dreambox-buildimage"
EXTRA_IMAGEDEPENDS += "dreambox-secondstage-${MACHINE}"
MACHINE_ESSENTIAL_EXTRA_RDEPENDS ?= ""

MACHINE_ESSENTIAL_EXTRA_RDEPENDS += "\
	dreambox-dvb-modules-${MACHINE}	\
	v4l-dvb-module-dvb-core	\
	v4l-dvb-module-stv0299 \
	firmware-rt73 \
	firmware-rtl8712u \
	firmware-zd1211 \
	\
	rtl871x \
	rt73 \
	kernel-module-zd1211rw \
"

KERNEL_MODULE_AUTOLOAD += "xfs"

MACHINE_EXTRA_RRECOMMENDS += "\
	gstreamer1.0-plugin-dvbmediasink \
	ntfs-3g \
	kernel-module-cifs \
	kernel-module-hmac \
	kernel-module-md4 \
	kernel-module-ecb \
	dreambox-boot-progress \
	"

GLIBC_EXTRA_OECONF = "--with-tls --with-__thread"
DVBMEDIASINK_CONFIG = "--with-dts=no"
OLDEST_KERNEL = "2.6.18"
GCCVERSION = "9.1.0"
GLIBCVERSION = "2.19"

PREFERRED_VERSION_gcc-cross-initial-${TARGET_ARCH} = ""
PREFERRED_VERSION_rtl8192cu = "3.3.0"
PREFERRED_VERSION_libnl = "3.2.24"
PREFERRED_VERSION_linux-dreambox = "2.6.18"
PREFERRED_VERSION_linux-libc-headers = "2.6.18"
PREFERRED_VERSION_wpa-supplicant = "0.5.8"
PREFERRED_VERSION_wscan = "20130331"
PREFERRED_VERSION_binutils = "2.28"
PREFERRED_VERSION_binutils-native = "2.28"
PREFERRED_VERSION_binutils-cross-${TARGET_ARCH} = "2.28"
PREFERRED_VERSION_binutils-crosssdk-${SDK_ARCH} = "2.28"
PREFERRED_VERSION_binutils-cross-canadian-${TRANSLATED_TARGET_ARCH} = "2.28"

MACHINE_FEATURES += "alsa usbhost 3dtv switchoff dreambox legacykernel"
# Add "dreambox" override for recipes
MACHINEOVERRIDES .= ":dreambox"

PREFERRED_PROVIDER_virtual/bootloader = "dreambox-secondstage-${MACHINE}"
PREFERRED_PROVIDER_virtual/kernel = "linux-dreambox"

PREFERRED_PROVIDER_virtual/blindscan-dvbs = "dreambox-blindscan-utils"
PREFERRED_PROVIDER_virtual/blindscan-dvbc = "dreambox-blindscan-utils"

KERNEL_IMAGETYPE = "vmlinux"
KERNEL_OUTPUT = "${KERNEL_IMAGETYPE}"
KERNEL_OUTPUT_DIR = "."
KERNEL_CONSOLE = "${@bb.utils.contains('MACHINE_FEATURES', 'usbconsole', 'ttyS0,115200', 'null', d)}"

DREAMBOX_BUILDIMAGE = "buildimage --arch ${MACHINE} ${EXTRA_BUILDCMD} \
	--erase-block-size ${DREAMBOX_ERASE_BLOCK_SIZE} \
	--flash-size ${DREAMBOX_FLASH_SIZE} \
	--sector-size ${DREAMBOX_SECTOR_SIZE}"

require conf/machine/include/tune-mips32.inc
