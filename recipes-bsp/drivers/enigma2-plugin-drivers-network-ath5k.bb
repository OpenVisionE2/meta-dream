SUMMARY = "Atheros 5xxx 802.11 kernrel driver"
inherit allarch

require conf/license/license-gplv2.inc

RRECOMMENDS_${PN} = "\
    kernel-module-ath5k \
"

do_compile() {
}

do_install() {
	install -d ${D}${sysconfdir}/modules-load.d
	echo "ath5k" > ${D}${sysconfdir}/modules-load.d/ath5k.conf
}

FILES_${PN} += "${sysconfdir}/moddules-load.d/ath5k.conf"

PV = "1.0"

ALLOW_EMPTY_${PN} = "1"


