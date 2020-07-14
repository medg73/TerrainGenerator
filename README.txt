terrain generator in java based on: https://mewo2.com/notes/terrain/
hex grid reference: https://www.redblobgames.com/grids/hexagons/

implementation: https://www.redblobgames.com/grids/hexagons/implementation.html

TODO:
wrapping map
make random terrain more realistic, or allow drawing
rivers - based on steepness of slope into hex and # of hexes draining into this one
    something not quite right with rivers? they can flow uphill b/c they are calculated after
        erosion
        riverpairs are directional edges, need to rewrite all that code
            change class name to directedEdge
            change equals and hashcode methods
            change high and low to source and sink
            each has a source and sink hex
            hexBoard should keep track of flow by riverpair, don't use member vbl
            river will be chain of directed edges
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
