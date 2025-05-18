<script setup>
import { computed, watch, onMounted, ref } from "vue";
import plotService from "../services/plotService";

const plots = ref([]);

onMounted(async () => {
  const res = await plotService.getAll();
  plots.value = res.data;
  console.log(plots.value[0]);
});

// Listen for simulation steps
const handleSimulationStep = async () => {
  await plotService.stepSimulation(); // Advance simulation on backend
  const res = await plotService.getAll(); // Fetch updated state
  plots.value = res.data;
  console.log("Has Benn Updated");
};

defineExpose({
  handleSimulationStep,
});

// const props = defineProps({
//   plots: {
//     type: Array,
//     default: () => [],
//   },
// });

const ROWS = 6;
const COLS = 6;

function getParcelle(x, y) {
  // if (!Array.isArray(plots.value)) return null;
  return plots.value.find((p) => p.x === x && p.y === y) || null;
}
</script>

<template>
  <div class="container mx-auto py-8">
    <h2 class="text-3xl font-bold mb-6 text-emerald-700 text-center">
      Grille du Potager
    </h2>
    <div class="flex flex-col items-center">
      <div class="inline-block">
        <div>
          <div v-for="row in ROWS" :key="row" class="flex">
            <div
              v-for="col in COLS"
              :key="col"
              class="w-20 h-20 m-1 flex items-center justify-center"
            >
              <template v-if="getParcelle(col, row)">
                <div
                  v-for="parcelle in [getParcelle(col, row)]"
                  :key="parcelle.id"
                  class="w-full h-full bg-green-100 rounded-lg shadow flex flex-col items-center justify-center relative hover:bg-green-200 transition-colors cursor-pointer group"
                >
                  <span
                    v-if="parcelle.plantes && parcelle.plantes.length > 0"
                    class="text-2xl"
                    >🌱</span
                  >
                  <span
                    v-if="
                      parcelle.plantes &&
                      parcelle.plantes.length > 0 &&
                      parcelle.plantes[0].fruits > 0
                    "
                    class="absolute top-1 left-1 text-red-500 text-lg font-bold flex items-center"
                  >
                    🍎 {{ parcelle.plantes[0].fruits }}
                  </span>
                  <span class="text-xs font-semibold text-green-700 mt-1">
                    {{
                      parcelle.plantes && parcelle.plantes.length > 0
                        ? parcelle.plantes[0].espece
                        : "Vide"
                    }}
                  </span>
                  <div
                    v-if="parcelle.insectes && parcelle.insectes.length > 0"
                    class="absolute bottom-1 right-1 bg-blue-200 text-blue-800 rounded-full px-2 py-0.5 text-xs font-bold shadow group-hover:bg-blue-300"
                  >
                    🐞 {{ parcelle.insectes.length }}
                  </div>
                </div>
              </template>
              <template v-else>
                <div
                  class="w-full h-full bg-gray-200 rounded-lg border-2 border-dashed border-gray-400 flex items-center justify-center"
                >
                  <span class="text-gray-400 text-lg">—</span>
                </div>
              </template>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
