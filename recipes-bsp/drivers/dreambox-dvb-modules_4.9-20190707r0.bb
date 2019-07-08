SRC_URI[dreamone.md5sum] = "7cc7bac7d3b9aa60ebbba7bc74dd9363"
SRC_URI[dreamone.sha256sum] = "0a89c4419e0815f5d5ea895b7ab556cc6fc2085638a2078be7904060d4ff7a07"

require dreambox-dvb-modules-meson.inc

SRC_URI += "file://LICENSE-CLOSE"

do_license() {
	mv ${WORKDIR}/LICENSE-CLOSE ${B}/LICENSE-CLOSE
}

addtask do_license before do_populate_lic after do_unpack
