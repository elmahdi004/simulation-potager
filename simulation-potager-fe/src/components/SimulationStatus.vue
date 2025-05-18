<template>
  <div class="max-w-xl mx-auto py-[10px]">
    <h2 class="text-2xl font-bold mb-6 text-indigo-700 text-center">
      Statut de la Simulation
    </h2>
    <div class="bg-white rounded-xl shadow-lg p-[30px] flex flex-col gap-4">
      <div class="flex justify-between items-center">
        <span class="font-semibold text-gray-700">Étape actuelle :</span>
        <span class="text-lg font-bold text-indigo-700">{{ status.step }}</span>
      </div>
      <div class="flex justify-between items-center">
        <span class="font-semibold text-gray-700">Nombre de plantes :</span>
        <span class="text-lg font-bold text-green-700">{{
          status.plants
        }}</span>
      </div>
      <div class="flex justify-between items-center">
        <span class="font-semibold text-gray-700">Nombre d'insectes :</span>
        <span class="text-lg font-bold text-blue-700">{{
          status.insects
        }}</span>
      </div>
      <div class="flex justify-between items-center">
        <span class="font-semibold text-gray-700">Dispositifs actifs :</span>
        <span class="text-lg font-bold text-purple-700">{{
          status.devices
        }}</span>
      </div>
      <!-- <div class="mt-4 p-4 bg-indigo-50 rounded-lg text-indigo-800 text-center">
        <span class="font-semibold">Résumé :</span>
        <span class="ml-2">{{ status.summary }}</span>
      </div> -->
    </div>
  </div>
</template>

<script setup>
import { defineProps } from "vue";

const props = defineProps({
  status: {
    type: Object,
    required: true,
    default: () => ({
      step: 0,
      plants: 0,
      insects: 0,
      devices: 0,
      summary: "",
    }),
  },
});
</script>

<style scoped></style>
