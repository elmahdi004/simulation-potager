<template>
  <div class="container mx-auto py-8">
    <h2 class="text-3xl font-bold mb-6 text-purple-700 text-center">
      Dispositifs de Traitement
    </h2>
    <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6">
      <div
        v-for="device in devices"
        :key="device.id"
        class="bg-white rounded-xl shadow-lg p-6 flex flex-col hover:scale-105 transition-transform"
      >
        <div class="flex items-center mb-4">
          <span class="text-2xl mr-2">
            <span v-if="device.type === 'Arrosage'">💧</span>
            <span v-else-if="device.type === 'Engrais'">🌾</span>
            <span v-else-if="device.type === 'Insecticide'">🧴</span>
            <span v-else>🔧</span>
          </span>
          <h3 class="text-xl font-semibold text-gray-800">
            {{ device.type || "Dispositif" }}
          </h3>
        </div>
        <div class="text-gray-700 mb-2">
          <span class="font-semibold">Programmes d'activation :</span>
          <ul class="ml-4 mt-1 list-disc">
            <li v-for="program in device.programmes" :key="program.id">
              Début : <span class="font-medium">{{ program.startStep }}</span
              >, Durée :
              <span class="font-medium">{{ program.duration }} pas</span>, Type
              : <span class="font-medium">{{ program.type }}</span>
            </li>
          </ul>
        </div>
        <div class="text-gray-700">
          <span class="font-semibold">Parcelle centrale :</span>
          <span
            class="bg-purple-100 text-purple-700 rounded-full px-3 py-0.5 text-xs font-bold mr-2 mb-2"
          >
            ({{ device.parcelle?.x }}, {{ device.parcelle?.y }})
          </span>
        </div>
        <div class="text-gray-700 mt-2">
          <span class="font-semibold">Rayon d'action :</span>
          <span class="font-medium">{{ device.rayon }}</span>
        </div>
      </div>
    </div>
    <div v-if="devices.length === 0" class="text-center text-gray-500 mt-10">
      Aucun dispositif à afficher.
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import deviceService from "../services/deviceService";

const devices = ref([]);

onMounted(async () => {
  const res = await deviceService.getAll();
  devices.value = res.data;
});
</script>

<style scoped></style>
