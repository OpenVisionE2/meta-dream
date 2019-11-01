DEPENDS = "libgcc"
PROVIDES = "linux-dreambox"

require linux-dreambox-one.inc

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "https://dreamboxupdate.com/download/opendreambox/${BPN}/${BPN}-v${PV}.tar.xz"
SRC_URI[md5sum] = "f084198603c2bdd580fa09de2f98527d"
SRC_URI[sha256sum] = "4809dfd2aff93c6bc6fe4439dbf768d3a2cb5c67a8235074e87c328517ab1fab"

KERNEL_CC += "${TOOLCHAIN_OPTIONS}"
KERNEL_LD += "${TOOLCHAIN_OPTIONS}"

S = "${WORKDIR}/${BPN}-v${PV}"

CMDLINE = "${@kernel_console(d)} root=/dev/mmcblk0p7 rootwait rootfstype=ext4 no_console_suspend"

COMPATIBLE_MACHINE = "^(dreamone)$"

DEFCONFIG = "meson64"

LINUX_VERSION = "4.9"
KERNEL_DEVICETREE = "dreamone.dtb"
KERNEL_IMAGETYPES = "Image.gz"

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
    	cp ${B}/arch/arm64/boot/dts/amlogic/${KERNEL_DEVICETREE} ${B}/arch/arm64/boot/
    fi
}

KERNEL_FLASH_ARGS = "-c '${CMDLINE}'"
