SUMMARY = "Linux Kernel"
SECTION = "kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"
PATCHREV = "ac6cc9511a5f70eaa584c63fc5c3de33cae1d0e7"

PACKAGE_ARCH = "${MACHINE_ARCH}"

COMPATIBLE_MACHINE = "dm800"

inherit kernel siteinfo machine_kernel_pr

PRECOMPILED_ARCH = "${MACHINE}"
LOCALVERSION = "-${PRECOMPILED_ARCH}"

PACKAGES_DYNAMIC = "kernel-*"

export OS = "Linux"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_OUTPUT = "vmlinux"
KERNEL_IMAGETYPE = "vmlinux"
KERNEL_IMAGEDEST = "/boot"

# By default, kernel.bbclass modifies package names to allow multiple kernels
# to be installed in parallel. We revert this change and rprovide the versioned
# package names instead, to allow only one kernel to be installed.
PKG_kernel-base = "kernel-base"
PKG_kernel-image = "kernel-image"
RPROVIDES_kernel-base = "kernel-${KERNEL_VERSION}"
RPROVIDES_kernel-image = "kernel-image-${KERNEL_VERSION} ${KERNEL_BUILTIN_MODULES}"

USB_ROOT = "/dev/sdb2"

CMDLINE_JFFS2 = "root=/dev/mtdblock3 rootfstype=jffs2 rw ${CMDLINE_CONSOLE}"
CMDLINE_UBI = "ubi.mtd=root root=ubi0:rootfs rootfstype=ubifs rw ${CMDLINE_CONSOLE}"
CMDLINE = "${@bb.utils.contains('IMAGE_FSTYPES', 'ubinfi', '${CMDLINE_UBI}', '${CMDLINE_JFFS2}', d)}"
USB_CMDLINE = "root=${USB_ROOT} rootdelay=10 rw ${CMDLINE_CONSOLE}"

SRC_URI = " \
        ${KERNELORG_MIRROR}/linux/kernel/v2.6/linux-${PV}.tar.bz2;name=kernel \
        http://sources.dreamboxupdate.com/download/kernel-patches/${P}-${PATCHREV}.patch.bz2;name=patch \
        http://download.filesystems.org/unionfs/unionfs-2.x/unionfs-2.5.11_for_2.6.18.8.diff.gz;name=unionfs \
        file://stblinux-2.6.18-extra-version-7.4.patch \
        file://stblinux-2.6.18-brcmnand-oob-raw-write-fix.patch \
        file://linux-2.6.18-fix-mips-crosscompile.patch \
        file://linux-2.6.18-fix-proc-cputype.patch \
        file://dvb-api-2.6.18-5.3.patch \
        file://linux-2.6.18-dvb-core-headers-20100904.patch \
        file://linux-2.6.18-dvb-frontends-headers-20100904.patch \
        file://stblinux-2.6.18-fixed-brcmnand-buffer-overflow.patch \
        file://stblinux-2.6.18-libata-revert-no-more-needed-change.patch \
        file://stblinux-2.6.18-libata-hdd-spinup-workaround.patch \
        file://kbuild-fix-make-incompatibility.patch \
        file://0001-MIPS-Fix-possible-hang-in-LL-SC-futex-loops.patch \
        file://0001-Add-support-for-FTDI-FT4232H-based-devices.patch \
        file://0001-proc-mounts_poll-make-consistent-to-mdstat_poll.patch \
        file://0001-fixed-broken-usb-with-gcc-4.6.x.patch \
        file://linux-2.6.18-fix-serial.patch \
        file://stblinux-2.6.18-hw-ecc-compatibility.patch \
        file://linux-2.6.18-include-asm.patch \
        file://linux-2.6.18-include-linux.patch \
        file://linux-2.6.18-mod_devicetable_h.patch \
        file://linux-2.6.18-3g-modems.patch \
        file://mips_refactor_page_dev0.patch \
        file://mkmakefile-make-3.82-fix-follow-bug-2323.patch \
	file://mips-utimensat.patch \
        file://mips-pps.patch \
	file://vermagic.patch \
	file://binutils-gcc6.patch \
	file://kernel-gcc7.patch \
	file://add_resolution_to_absinfo.patch \
	file://add_strcasecmp.patch \
	file://extra_ipv6_headers.patch \
	file://dm-ioctl-add-data-to-struct.patch \
	file://defconfig \
"

SRC_URI[kernel.md5sum] = "296a6d150d260144639c3664d127d174"
SRC_URI[kernel.sha256sum] = "c95280ff6c5d2a17788f7cc582d23ae8a9a7ba3f202ec6e4238eaadfce7c163d"
SRC_URI[patch.md5sum] = "d8938aa5b1a5c6928a1fad3c699bd98e"
SRC_URI[patch.sha256sum] = "faca8966d65932619bf69d034ed892ac9bb5f5c9b57ba50ba8dbe471894105ac"
SRC_URI[unionfs.md5sum] = "c0c838b717f98a19a09483fb10e7299e"
SRC_URI[unionfs.sha256sum] = "b2e04936254bbf778c963de862061027c858a2e157bb2e48c773d2ed2445282e"

S = "${WORKDIR}/linux-${PV}"
B = "${WORKDIR}/build"

do_configure_prepend() {
	rm -rf ${STAGING_KERNEL_DIR}/.cofig
	rm -rf ${STAGING_KERNEL_DIR}/.config
	rm -rf ${STAGING_KERNEL_DIR}/.config.old
	rm -rf ${STAGING_KERNEL_DIR}/include/generated
	rm -rf ${STAGING_KERNEL_DIR}/include/config
	rm -rf ${STAGING_KERNEL_DIR}/arch/mips/include/generated

	echo "" > ${S}/.config
	echo "CONFIG_CMDLINE=\"${CMDLINE}\"" >> ${S}/.config

        sed -e '/CONFIG_AEABI/d' \
            -e '/CONFIG_OABI_COMPAT=/d' \
            -e '/CONFIG_CMDLINE=/d' \
            -e '/CONFIG_CPU_BIG_ENDIAN/d' \
            -e '/CONFIG_LOGO=/d' \
            -e '/CONFIG_LOGO_LINUX_CLUT224=/d' \
            -e '/CONFIG_LOCALVERSION/d' \
            -e '/CONFIG_LOCALVERSION_AUTO/d' \
	    < '${WORKDIR}/defconfig' >>'${S}/.config'

	echo 'CONFIG_LOCALVERSION="${LOCALVERSION}"' >>${S}/.config
	echo '# CONFIG_LOCALVERSION_AUTO is not set' >>${S}/.config
}

do_shared_workdir_prepend() {
	mkdir -p ${B}/include/generated/
	mkdir -p ${STAGING_KERNEL_BUILDDIR}/include
	mkdir -p ${STAGING_KERNEL_BUILDDIR}/scripts
	cp -fR ${B}/include/linux/* ${B}/include/generated/
	cp -fR ${B}/include/* ${STAGING_KERNEL_BUILDDIR}/include/
	cp -fR ${B}/scripts/* ${STAGING_KERNEL_BUILDDIR}/scripts/
	ln -fs ${STAGING_KERNEL_DIR} ${STAGING_KERNEL_BUILDDIR}/source
	ln -sf ${STAGING_KERNEL_DIR}/include/asm-mips ${STAGING_KERNEL_DIR}/include/asm
	if [ ! -e ${B}/Module.symvers ]
	then
		touch ${B}/Module.symvers
	fi
}

do_install_append() {
	${STRIP} ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
	gzip -9 ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
	echo "/boot/bootlogo-${PRECOMPILED_ARCH}.elf.gz filename=/boot/bootlogo-${PRECOMPILED_ARCH}.jpg" > ${D}/${KERNEL_IMAGEDEST}/autoexec.bat
	echo "/boot/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}.gz ${CMDLINE}" >> ${D}/${KERNEL_IMAGEDEST}/autoexec.bat
}

# For packages that RDEPEND on particular kernel modules, list the ones built into
# the kernel here, so that it is known that the kernel has them built in.
KERNEL_BUILTIN_MODULES = ""

do_rm_work() {
}

do_install_prepend() {
	mkdir -p ${S}/tools
}

do_package_qa() {
    exit 0
}

pkg_preinst_kernel-image() {
	if [ -z "$D" ]
	then
		if mountpoint -q /${KERNEL_IMAGEDEST}
		then
			mount -o remount,rw,compr=none /${KERNEL_IMAGEDEST}
		else
			mount -t jffs2 -o rw,compr=none mtd:boot /${KERNEL_IMAGEDEST} || mount -t jffs2 -o rw,compr=none mtd:'boot partition' /${KERNEL_IMAGEDEST}
		fi
	fi
}
pkg_prerm_kernel-image() {
	if [ -z "$D" ]
	then
		if mountpoint -q /${KERNEL_IMAGEDEST}
		then
			mount -o remount,rw,compr=none /${KERNEL_IMAGEDEST}
		else
			mount -t jffs2 -o rw,compr=none mtd:boot /${KERNEL_IMAGEDEST}
		fi
	fi
}
pkg_postinst_kernel-image() {
        if [ -z "$D" ] && mountpoint -q /${KERNEL_IMAGEDEST}; then
                if grep -q '\<root=/dev/mtdblock3\>' /proc/cmdline && grep -q '\<root=ubi0:rootfs\>' /boot/autoexec.bat; then
                        sed -ie 's!${CMDLINE_UBI}!${CMDLINE_JFFS2}!' /boot/autoexec.bat;
                fi
                umount /${KERNEL_IMAGEDEST};
        fi
}
pkg_postrm_kernel-image() {
	if [ -z "$D" ]
	then
		umount /${KERNEL_IMAGEDEST}
	fi
}

# Do not use update-alternatives!
pkg_postinst_kernel () {
}
pkg_postrm_kernel () {
}

FILES_kernel-vmlinux += " boot/vmlinux-2.6.18-7.4-${MACHINE}.gz"
FILES_kernel-image += " ${KERNEL_IMAGEDEST}/autoexec*.bat"
