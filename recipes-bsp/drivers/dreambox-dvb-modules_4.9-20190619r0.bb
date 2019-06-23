SRC_URI[dreamone.md5sum] = "7866a108cf500971fd9728c12b1fa002"
SRC_URI[dreamone.sha256sum] = "fed5268d237acd392b6d801261343fb79318ab4dba840fc669fc00ceef581ec5"

require dreambox-dvb-modules-meson.inc

SRC_URI += "file://LICENSE-CLOSE"

do_license() {
	mv ${WORKDIR}/LICENSE-CLOSE ${B}/LICENSE-CLOSE
}

addtask do_license before do_populate_lic after do_unpack
