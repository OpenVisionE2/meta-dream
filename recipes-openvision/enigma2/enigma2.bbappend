FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append_dm800 += "\
    file://e2_old_dvbapi.patch \
"
