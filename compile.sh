#!/bin/bash
sh ./recompile.sh
sh ./reobfuscate_srg.sh
cp -urv reobf/minecraft/sbfp .
cp -urv src/minecraft/mods .
jar cf sbfptech-$1.jar sbfp mods mcmod.info