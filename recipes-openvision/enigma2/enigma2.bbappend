FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PYTHON_RDEPS += "\
	python-service-identity \
"

SRC_URI_append_dm800 += "\
    file://e2_old_dvbapi.patch \
"
