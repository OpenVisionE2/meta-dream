DEPENDS = "libgcc"
PROVIDES = "linux-dreambox"
PE = "1"

PACKAGE_ARCH = "${MACHINE_ARCH}"

require recipes-bsp/linux/linux-dreambox-dmamlogic.inc

SRC_URI = "https://dreamboxupdate.com/download/opendreambox/linux-meson64/linux-meson64-v${PV}.tar.xz \
	file://${OPENVISION_BASE}/meta-openvision/recipes-linux/kernel-patches/kernel-add-support-for-gcc${VISIONGCCVERSION}.patch \
	file://defconfig \
	"

SRC_URI[md5sum] = "c0250b87218995f4f0b1d9bffd6dd597"
SRC_URI[sha256sum] = "86eaa6ed3a40178abfea63b6569b5bd1a8e671c180738d7929caf2c8edec1a13"

KERNEL_CC += "${TOOLCHAIN_OPTIONS}"
KERNEL_LD += "${TOOLCHAIN_OPTIONS}"

S = "${WORKDIR}/linux-meson64-v${PV}"

CMDLINE = "logo=osd0,loaded,0x7f800000 vout=2160p50hz,enable hdmimode=2160p50hz fb_width=1280 fb_height=720 ${@kernel_console(d)} root=/dev/mmcblk0p7 rootwait rootfstype=ext4 no_console_suspend"

COMPATIBLE_MACHINE = "^(dreamone|dreamtwo)$"

# DEFCONFIG = "meson64"

LINUX_VERSION = "4.9"
KERNEL_IMAGETYPES = "Image.gz"

export KCFLAGS = "-Wno-error"

do_compile_append() {
    if test -n "${KERNEL_DEVICETREE}"; then
    	for DTB in ${KERNEL_DEVICETREE}; do
    		if echo ${DTB} | grep -q '/dts/'; then
    			bbwarn "${DTB} contains the full path to the the dts file, but only the dtb name should be used."
    			DTB=`basename ${DTB} | sed 's,\.dts$,.dtb,g'`
    		fi
    		oe_runmake ${DTB}
    	done
    	# Create directory, this is needed for out of tree builds
    	mkdir -p ${B}/arch/arm64/boot/dts/amlogic/
    	cp -f ${B}/arch/arm64/boot/dts/amlogic/${KERNEL_DEVICETREE} ${B}/arch/arm64/boot/
    fi
}

KERNEL_FLASH_ARGS = "-c '${CMDLINE}'"

do_rm_work() {
}