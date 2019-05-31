#!/usr/bin/env bash

regex=""
input=""

if [ "$1" == "" ]; then
    echo "Regex size missing"
    exit 1
fi

i=1
while [ "$i" -le "$1" ]; do
    regex="a*${regex}a"
    input="${input}a"
    i=$((i + 1))
done

echo "Regex: (a*)^n a^n, Input: a^n, n = $1"

time (echo ${input} | grep "${regex}")

regex=""
input=""
i=1
while [ "$i" -le "$1" ]; do
    regex="${regex}a"
    input="${input}a"
    i=$((i + 1))
done

echo "Regex: a^n, Input: a^n, n = $1"

time (echo ${input} | grep "${regex}")
