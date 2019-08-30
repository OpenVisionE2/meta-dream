SRC_URI[dreamone.md5sum] = "7481bda80eea1621dd6777135c599994"
SRC_URI[dreamone.sha256sum] = "8c2738f47553866011cd6367ecda1a4dc23e64e948eb0cb0ea18fd27e1143652"

require dreambox-dvb-modules-meson.inc

SRC_URI += "file://LICENSE-CLOSE"

do_license() {
	mv ${WORKDIR}/LICENSE-CLOSE ${B}/LICENSE-CLOSE
}

addtask do_license before do_populate_lic after do_unpack
