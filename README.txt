terrain generator in java based on: https://mewo2.com/notes/terrain/
hex grid reference: https://www.redblobgames.com/grids/hexagons/

implementation: https://www.redblobgames.com/grids/hexagons/implementation.html

TODO:
change hexlib to use List instead of [] in returns from public methods?
fixup UI to have drawing panel with different things to draw
hexboard factory class
clean up hexpanel

wrapping map
make random terrain more realistic, or allow drawing
rivers - based on steepness of slope into hex and # of hexes draining into this one
only draw rivers if there is enough flow?
draw lakes when adding water level
    make sea level adjustable?
add world map view

zooming
bug - when zooming out, if you are off the panel you lose it - zooming should always keep panel in view
be able to zoom in

draw terrain
cleanup hexpanel
cleanup hexboard

game stuff:
sprites and movement cost
roads
