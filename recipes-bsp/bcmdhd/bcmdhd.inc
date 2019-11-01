SUMMARY = "Broadcom DHD driver"
LICENSE = "GPLv2"
DEPENDS = "virtual/kernel"

inherit module-base module machine_kernel_pr

M ?= "${S}"

EXTRA_OEMAKE += " \
    ARCH=${ARCH} \
    CONFIG_BCMDHD_SDIO=y \
    CROSS_COMPILE=${TARGET_PREFIX} \
    DEPMOD=echo \
    INSTALL_MOD_PATH=${D} \
    KDIR=${STAGING_KERNEL_BUILDDIR} \
    M=${M} \
"

do_compile() {
    unset CC CFLAGS CPP CPPFLAGS CXX CXXFLAGS CCLD LDFLAGS
    oe_runmake -C ${STAGING_KERNEL_BUILDDIR} modules
}
do_install() {
    unset CC CFLAGS CPP CPPFLAGS CXX CXXFLAGS CCLD LDFLAGS
    oe_runmake -C ${STAGING_KERNEL_BUILDDIR} modules_install

    install -d ${D}${sysconfdir}/modules-load.d
    echo dhd > ${D}${sysconfdir}/modules-load.d/00-${BPN}.conf
    chmod 0644 ${D}${sysconfdir}/modules-load.d/00-${BPN}.conf
}

FILES_${PN} = "${sysconfdir}/modules-load.d \
               /lib/modules/${KERNEL_VERSION}/extra"

pkg_postinst_${PN} () {
if [ -z "$D" ]; then
	depmod -a ${KERNEL_VERSION}
fi
}

RDEPENDS_${PN} = "bcmdhd-firmware"
