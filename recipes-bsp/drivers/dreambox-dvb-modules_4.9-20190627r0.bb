SRC_URI[dreamone.md5sum] = "e81d79e3f9dded015260643dba2a3e26"
SRC_URI[dreamone.sha256sum] = "a87ee3cde904415742646f3c43f438b530cb3d8cc09a3ffcbfe4cc8ed75600fc"

require dreambox-dvb-modules-meson.inc

SRC_URI += "file://LICENSE-CLOSE"

do_license() {
	mv ${WORKDIR}/LICENSE-CLOSE ${B}/LICENSE-CLOSE
}

addtask do_license before do_populate_lic after do_unpack
