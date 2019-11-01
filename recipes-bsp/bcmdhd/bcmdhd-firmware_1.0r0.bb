SRC_URI[all.md5sum] = "6dceb8e2eeed94d6d19ca6fff2993802"
SRC_URI[all.sha256sum] = "a09a769e5513a85ebbc11f29696b1cb8bf67555cd0b44339420704ecd8b9774b"

inherit opendreambox-precompiled-binary-new

require bcmdhd-firmware.inc

SRC_URI += "file://LICENSE-CLOSE"

do_license() {
	mv ${WORKDIR}/LICENSE-CLOSE ${B}/LICENSE-CLOSE
}

addtask do_license before do_populate_lic after do_unpack

COMPATIBLE_MACHINE = "^(dreamone)$"
