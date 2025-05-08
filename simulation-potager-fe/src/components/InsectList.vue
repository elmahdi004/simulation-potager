<template>
  <div class="container mx-auto py-8">
    <h2 class="text-3xl font-bold mb-6 text-blue-700 text-center">
      Liste des Insectes
    </h2>
    <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6">
      <div
        v-for="insect in insects"
        :key="insect.id"
        class="bg-white rounded-xl shadow-lg p-6 flex flex-col items-center hover:scale-105 transition-transform"
      >
        <div
          class="w-16 h-16 bg-blue-200 rounded-full flex items-center justify-center mb-4"
        >
          <span class="text-2xl font-bold text-blue-700">🐞</span>
        </div>
        <h3 class="text-xl font-semibold text-gray-800 mb-2">
          {{ insect.espece }}
        </h3>
        <p class="text-gray-600">
          Sexe : <span class="font-medium">{{ insect.sexe }}</span>
        </p>
        <p class="text-gray-600">
          Indice de santé :
          <span :class="insect.sante > 5 ? 'text-green-600' : 'text-red-500'">{{
            insect.sante
          }}</span>
        </p>
        <p class="text-gray-600">
          Mobilité : <span class="font-medium">{{ insect.mobilite }}</span>
        </p>
        <p class="text-gray-600">
          Résistance Insecticide :
          <span class="font-medium">{{ insect.resistanceInsecticide }}</span>
        </p>
        <p class="text-gray-600" v-if="insect.parcelle">
          Parcelle : <span class="font-medium">{{ insect.parcelle.id }}</span>
        </p>
      </div>
    </div>
    <div v-if="insects.length === 0" class="text-center text-gray-500 mt-10">
      Aucun insecte à afficher.
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import insectService from "../services/insectService";

const insects = ref([]);

onMounted(async () => {
  const res = await insectService.getAll();
  insects.value = res.data;
});
</script>

<style scoped></style>
