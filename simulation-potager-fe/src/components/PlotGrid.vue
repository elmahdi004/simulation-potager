<template>
  <div class="container mx-auto py-8">
    <h2 class="text-3xl font-bold mb-6 text-emerald-700 text-center">
      Grille du Potager
    </h2>
    <div class="flex flex-col items-center">
      <div class="grid" :style="gridStyle">
        <div
          v-for="plot in plots"
          :key="plot.id"
          class="w-24 h-24 m-1 bg-green-100 rounded-lg shadow flex flex-col items-center justify-center relative hover:bg-green-200 transition-colors cursor-pointer group"
        >
          <div v-if="plot.plant" class="flex flex-col items-center">
            <span class="text-2xl">🌱</span>
            <span class="text-xs font-semibold text-green-700 mt-1">{{
              plot.plant.species
            }}</span>
          </div>
          <div v-else class="text-gray-400 text-sm">Vide</div>
          <div
            v-if="plot.insects && plot.insects.length > 0"
            class="absolute bottom-1 right-1 bg-blue-200 text-blue-800 rounded-full px-2 py-0.5 text-xs font-bold shadow group-hover:bg-blue-300"
          >
            🐞 {{ plot.insects.length }}
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "PlotGrid",
  data() {
    return {
      rows: 5,
      cols: 6,
      plots: [
        { id: "A1", plant: { species: "Tomate" }, insects: [{}, {}] },
        { id: "A2", plant: { species: "Carotte" }, insects: [] },
        { id: "A3", plant: null, insects: [{}, {}, {}] },
        { id: "A4", plant: { species: "Laitue" }, insects: [{}] },
        { id: "A5", plant: null, insects: [] },
        { id: "A6", plant: { species: "Aubergine" }, insects: [] },
        { id: "B1", plant: null, insects: [] },
        { id: "B2", plant: { species: "Tomate" }, insects: [{}] },
        { id: "B3", plant: null, insects: [] },
        { id: "B4", plant: null, insects: [] },
        { id: "B5", plant: { species: "Carotte" }, insects: [{}, {}] },
        { id: "B6", plant: null, insects: [] },
        // ...add more plots as needed for the grid
      ],
    };
  },
  computed: {
    gridStyle() {
      return {
        display: "grid",
        gridTemplateColumns: `repeat(${this.cols}, minmax(0, 1fr))`,
        gridTemplateRows: `repeat(${this.rows}, minmax(0, 1fr))`,
      };
    },
  },
};
</script>

<style scoped></style>
