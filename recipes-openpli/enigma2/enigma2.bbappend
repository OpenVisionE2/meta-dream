FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append_dm800 = " \
    file://get-rid-of-HAVE-OLDE2-API-condition.patch \
"
