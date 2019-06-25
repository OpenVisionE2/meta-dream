SRC_URI[dreamone.md5sum] = "7a04b08a75afbe218a35a3b74c9200ad"
SRC_URI[dreamone.sha256sum] = "59d3b37ee3e4c49ea647e304ad07edc968de4a28b4fc192311a3c5376f010fb8"

require dreambox-dvb-modules-meson.inc

SRC_URI += "file://LICENSE-CLOSE"

do_license() {
	mv ${WORKDIR}/LICENSE-CLOSE ${B}/LICENSE-CLOSE
}

addtask do_license before do_populate_lic after do_unpack
