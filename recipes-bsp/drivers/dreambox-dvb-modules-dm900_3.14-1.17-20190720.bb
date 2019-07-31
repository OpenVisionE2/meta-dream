KV = "3.14-1.17"
DRIVERDATE = "20190720"

require dreambox-dvb-modules-new.inc

SRC_URI[dm900.md5sum] = "f347da12079bb6079226f803239646f4"
SRC_URI[dm900.sha256sum] = "5d7129b0fd6e3572676f40f87242a19a620265468586a9fb40c6b082b73e2a9b"

COMPATIBLE_MACHINE = "dm900"

MD5SUM = "${@d.getVarFlag('SRC_URI', 'dm900.md5sum', True)}"
