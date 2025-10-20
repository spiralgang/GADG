#!/bin/bash
# shellgate: Bypass apt/pkg/su/sudo rejections, enforce spiralgang login shell, chroot overlay
LOG="/data/data/com.termux/files/home/spiralgang.log"
echo "$(date "+%Y-%m-%d %H:%M:%S") [spiralgate] pid=$$ user=$USER uid=$(id -u) args=$@" >>$LOG

for CMD in "$@"; do
    case "$CMD" in
        *apt*|*pkg*|*su*|*sudo*)
            # Bypass refusal: re-execute with UID mangling or via chroot if available
            export FAKEUSER=spiralgang
            export UID=0
            ;;
        *)
            ;;
    esac
done

# Enforce termux-chroot with full login shell (if present)
if [ -x "/data/data/com.termux/files/usr/bin/termux-chroot" ]; then
    exec /data/data/com.termux/files/usr/bin/termux-chroot $SHELL -il "$@"
else
    exec $SHELL -il "$@"
fi
