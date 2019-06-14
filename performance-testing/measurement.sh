#!/usr/bin/env bash

regex="\(a\|aa\)*b"
inputBase=""

if [ "$1" == "" ]; then
    echo "Input size missing"
    exit 1
fi

i=1
while [ "$i" -le "$1" ]; do
    inputBase="${inputBase}a"
    i=$((i + 1))
done

echo "Regex: (a|aa)*b, Input: a^nb, n = $1"
input="${inputBase}b"
time for i in {1..10}; do (echo ${input} | grep -c "${regex}"); done

echo "Regex: (a|aa)*b, Input: a^nc, n = $1"
input="${inputBase}c"
time for i in {1..10}; do (echo ${input} | grep -c "${regex}"); done

regex="\(a*a*\)*b"
echo "Regex: (a*a*)*b, Input: a^nb, n = $1"
input="${inputBase}b"
time for i in {1..10}; do (echo ${input} | grep -c "${regex}"); done

echo "Regex: (a*a*)*b, Input: a^nc, n = $1"
input="${inputBase}c"
time for i in {1..10}; do (echo ${input} | grep -c "${regex}"); done
