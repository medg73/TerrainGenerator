terrain generator in java based on: https://mewo2.com/notes/terrain/
hex grid reference: https://www.redblobgames.com/grids/hexagons/

implementation: https://www.redblobgames.com/grids/hexagons/implementation.html

TODO:
wrapping map
when you grab scrollbar and scroll rivers don't draw right
make random terrain more realistic, or allow drawing
rivers - based on steepness of slope into hex and # of hexes draining into this one
    something not quite right with rivers? they can flow uphill b/c they are calculated after

    lakes
    get flow through rivers - only draw them if there is enough flow?
add world map view

zooming
bug - when zooming out, if you are off the panel you lose it - zooming should always keep panel in view
be able to zoom in

draw terrain

game stuff:
sprites and movement cost
roads
