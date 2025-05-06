<template>
  <div class="min-h-screen bg-gray-50 flex flex-col">
    <!-- Header with controls -->
    <header class="bg-white shadow-md py-4 px-6">
      <div
        class="max-w-7xl mx-auto flex flex-col md:flex-row md:items-center justify-between gap-4"
      >
        <div class="flex items-center">
          <router-link to="/" class="flex items-center mr-6">
            <div class="bg-green-600 p-1 rounded-full mr-2">
              <svg
                class="w-6 h-6 text-white"
                viewBox="0 0 24 24"
                fill="none"
                stroke="currentColor"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M12 19l9 2-9-18-9 18 9-2zm0 0v-8"
                />
              </svg>
            </div>
            <span class="text-xl font-semibold text-gray-800"
              >Potager Automatisé</span
            >
          </router-link>
          <div class="flex space-x-2">
            <button
              @click="toggleSimulation"
              :class="[
                isRunning
                  ? 'bg-red-500 hover:bg-red-600'
                  : 'bg-green-600 hover:bg-green-700',
                'text-white px-3 py-2 rounded-md transition-colors duration-200 flex items-center',
              ]"
            >
              <span v-if="isRunning" class="flex items-center">
                <svg
                  class="w-5 h-5 mr-1"
                  fill="none"
                  stroke="currentColor"
                  viewBox="0 0 24 24"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M6 18L18 6M6 6l12 12"
                  ></path>
                </svg>
                Arrêter
              </span>
              <span v-else class="flex items-center">
                <svg
                  class="w-5 h-5 mr-1"
                  fill="none"
                  stroke="currentColor"
                  viewBox="0 0 24 24"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M14.752 11.168l-3.197-2.132A1 1 0 0010 9.87v4.263a1 1 0 001.555.832l3.197-2.132a1 1 0 000-1.664z"
                  ></path>
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M21 12a9 9 0 11-18 0 9 9 0 0118 0z"
                  ></path>
                </svg>
                Lancer
              </span>
            </button>
            <button
              @click="stepForward"
              class="bg-blue-500 hover:bg-blue-600 text-white px-3 py-2 rounded-md transition-colors duration-200 flex items-center"
              :disabled="isRunning"
              :class="{ 'opacity-50 cursor-not-allowed': isRunning }"
            >
              <svg
                class="w-5 h-5 mr-1"
                fill="none"
                stroke="currentColor"
                viewBox="0 0 24 24"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M13 5l7 7-7 7M5 5l7 7-7 7"
                ></path>
              </svg>
              Pas à pas
            </button>
            <button
              @click="resetSimulation"
              class="bg-gray-500 hover:bg-gray-600 text-white px-3 py-2 rounded-md transition-colors duration-200 flex items-center"
            >
              <svg
                class="w-5 h-5 mr-1"
                fill="none"
                stroke="currentColor"
                viewBox="0 0 24 24"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M4 4v5h.582m15.356 2A8.001 8.001 0 004.582 9m0 0H9m11 11v-5h-.581m0 0a8.003 8.003 0 01-15.357-2m15.357 2H15"
                ></path>
              </svg>
              Réinitialiser
            </button>
          </div>
        </div>

        <div class="flex items-center space-x-4">
          <div class="flex items-center">
            <label for="speed" class="mr-2 text-sm font-medium text-gray-700"
              >Vitesse:</label
            >
            <select
              id="speed"
              v-model="simulationSpeed"
              class="bg-white border border-gray-300 rounded-md px-2 py-1 text-sm"
            >
              <option :value="1000">Lente</option>
              <option :value="500">Normale</option>
              <option :value="200">Rapide</option>
              <option :value="50">Très rapide</option>
            </select>
          </div>

          <div class="bg-gray-100 px-3 py-1 rounded-md flex items-center">
            <span class="text-sm font-medium text-gray-700 mr-2">Jour:</span>
            <span class="text-sm font-bold text-gray-900">{{
              simulationDay
            }}</span>
          </div>

          <div class="bg-gray-100 px-3 py-1 rounded-md flex items-center">
            <span class="text-sm font-medium text-gray-700 mr-2">Pas:</span>
            <span class="text-sm font-bold text-gray-900">{{
              simulationStep
            }}</span>
          </div>
        </div>
      </div>
    </header>

    <!-- Main content area -->
    <main class="flex-grow flex overflow-hidden">
      <!-- Left sidebar - Tools -->
      <div class="w-64 bg-white shadow-md overflow-y-auto p-4 flex flex-col">
        <h2 class="text-lg font-semibold text-gray-800 mb-4">Outils</h2>

        <!-- Tool selection -->
        <div class="space-y-3 mb-6">
          <button
            v-for="tool in tools"
            :key="tool.id"
            @click="selectTool(tool.id)"
            :class="[
              selectedTool === tool.id
                ? 'bg-blue-100 border-blue-500'
                : 'bg-gray-50 border-gray-200 hover:bg-gray-100',
              'w-full flex items-center p-3 border rounded-md transition-colors duration-200',
            ]"
          >
            <div class="w-8 h-8 flex items-center justify-center mr-3">
              <component
                :is="tool.icon"
                class="w-6 h-6"
                :class="tool.iconColor"
              />
            </div>
            <div class="text-left">
              <p class="font-medium text-gray-800">{{ tool.name }}</p>
              <p class="text-xs text-gray-500">{{ tool.description }}</p>
            </div>
          </button>
        </div>

        <!-- Current selection details -->
        <div v-if="selectedCell" class="bg-gray-50 p-4 rounded-md mt-auto">
          <h3 class="font-semibold text-gray-800 mb-2">
            Parcelle sélectionnée
          </h3>
          <div class="text-sm">
            <p>
              <span class="font-medium">Position:</span> ({{ selectedCell.x }},
              {{ selectedCell.y }})
            </p>
            <p>
              <span class="font-medium">Humidité:</span>
              {{ Math.round(selectedCell.moisture * 100) }}%
            </p>
            <p>
              <span class="font-medium">Plantes:</span>
              {{ selectedCell.plants.length }}
            </p>
            <p>
              <span class="font-medium">Insectes:</span>
              {{ selectedCell.insects.length }}
            </p>
            <button
              v-if="selectedCell"
              @click="showDetails"
              class="mt-2 text-blue-600 text-xs underline"
            >
              Voir les détails
            </button>
          </div>
        </div>
      </div>

      <!-- Main content - Grid -->
      <div class="flex-grow overflow-auto p-6 bg-gray-100">
        <div class="max-w-4xl mx-auto bg-white rounded-lg shadow-md p-6">
          <h2 class="text-xl font-bold text-gray-800 mb-4">Vue du Potager</h2>

          <!-- Grid representation -->
          <div
            class="grid gap-1"
            :style="{
              'grid-template-columns': `repeat(${gridSize}, minmax(0, 1fr))`,
              'grid-template-rows': `repeat(${gridSize}, minmax(0, 1fr))`,
            }"
          >
            <div
              v-for="(cell, index) in grid"
              :key="index"
              @click="selectCell(cell)"
              :class="[
                cell.moisture > 0.7
                  ? 'bg-blue-100'
                  : cell.moisture > 0.4
                  ? 'bg-green-50'
                  : 'bg-yellow-50',
                selectedCell &&
                selectedCell.x === cell.x &&
                selectedCell.y === cell.y
                  ? 'ring-2 ring-blue-500'
                  : '',
                'relative w-full aspect-square rounded-sm cursor-pointer hover:ring-2 hover:ring-blue-300 transition-all duration-200',
              ]"
            >
              <!-- Plants -->
              <div class="absolute inset-0 flex items-center justify-center">
                <div v-if="cell.plants.length > 0" class="relative">
                  <svg
                    v-for="(plant, plantIndex) in cell.plants.slice(0, 3)"
                    :key="plantIndex"
                    class="w-full h-full absolute"
                    :class="getPlantColorClass(plant)"
                    :style="{
                      top: `${plantIndex * 2}px`,
                      left: `${plantIndex * 2}px`,
                    }"
                    viewBox="0 0 24 24"
                    fill="currentColor"
                  >
                    <path
                      d="M12,3c0,0-6.186,5.34-6.186,11.7c0,3.432,2.754,6.186,6.186,6.186c3.432,0,6.186-2.754,6.186-6.186
                        C18.186,8.34,12,3,12,3z M12,17.598c-1.824,0-3.308-1.483-3.308-3.308c0-0.289,0.227-0.515,0.515-0.515
                        s0.515,0.227,0.515,0.515c0,1.269,1.008,2.277,2.277,2.277c0.289,0,0.515,0.227,0.515,0.515S12.289,17.598,12,17.598z"
                    />
                  </svg>
                </div>
              </div>

              <!-- Insects -->
              <div
                v-if="cell.insects.length > 0"
                class="absolute top-0 right-0 w-3 h-3 bg-yellow-400 rounded-full flex items-center justify-center text-xs text-white font-bold"
              >
                {{ cell.insects.length > 9 ? "9+" : cell.insects.length }}
              </div>

              <!-- Treatment device -->
              <div v-if="cell.device" class="absolute bottom-0 right-0">
                <div
                  class="w-4 h-4 rounded-full flex items-center justify-center"
                  :class="{
                    'bg-blue-500': cell.device.type === 'water',
                    'bg-red-500': cell.device.type === 'insecticide',
                    'bg-green-500': cell.device.type === 'fertilizer',
                  }"
                >
                  <svg
                    class="w-3 h-3 text-white"
                    viewBox="0 0 24 24"
                    fill="none"
                    stroke="currentColor"
                  >
                    <path
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      stroke-width="2"
                      d="M19 11a7 7 0 01-7 7m0 0a7 7 0 01-7-7m7 7v4m0 0H8m4 0h4m-4-8a3 3 0 01-3-3V5a3 3 0 116 0v6a3 3 0 01-3 3z"
                    />
                  </svg>
                </div>
              </div>

              <!-- Coordinates -->
              <div
                class="absolute bottom-0 left-0 text-xs text-gray-500 font-mono"
              >
                {{ cell.x }},{{ cell.y }}
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Right sidebar - Information panel -->
      <div class="w-72 bg-white shadow-md overflow-y-auto p-4">
        <h2 class="text-lg font-semibold text-gray-800 mb-4">Informations</h2>

        <!-- Stats cards -->
        <div class="space-y-4 mb-6">
          <div
            v-for="(stat, index) in stats"
            :key="index"
            class="bg-gray-50 p-3 rounded-md"
          >
            <div class="flex items-center">
              <div class="w-8 h-8 flex items-center justify-center mr-3">
                <component
                  :is="stat.icon"
                  class="w-6 h-6"
                  :class="stat.iconColor"
                />
              </div>
              <div>
                <p class="text-sm font-medium text-gray-700">{{ stat.name }}</p>
                <p class="text-lg font-bold" :class="stat.valueColor">
                  {{ stat.value }}
                </p>
              </div>
            </div>
          </div>
        </div>

        <!-- Events feed -->
        <div class="mt-6">
          <h3 class="font-semibold text-gray-800 mb-2 flex items-center">
            <svg
              class="w-4 h-4 mr-1"
              fill="none"
              stroke="currentColor"
              viewBox="0 0 24 24"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"
              />
            </svg>
            Événements récents
          </h3>
          <div class="space-y-2">
            <div
              v-for="(event, index) in events"
              :key="index"
              :class="[
                'p-2 rounded-md text-sm border-l-4',
                event.type === 'info'
                  ? 'bg-blue-50 border-blue-500'
                  : event.type === 'warning'
                  ? 'bg-yellow-50 border-yellow-500'
                  : event.type === 'success'
                  ? 'bg-green-50 border-green-500'
                  : 'bg-red-50 border-red-500',
              ]"
            >
              <p class="font-medium">{{ event.title }}</p>
              <p class="text-gray-600 text-xs">{{ event.time }}</p>
            </div>
          </div>
        </div>
      </div>
    </main>

    <!-- Detail modal -->
    <div
      v-if="showModal"
      class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50"
    >
      <div
        class="bg-white rounded-lg shadow-xl max-w-2xl w-full max-h-[80vh] overflow-auto"
      >
        <div class="p-6">
          <div class="flex justify-between items-center mb-4">
            <h3 class="text-xl font-bold text-gray-800">
              Détails de la parcelle ({{ selectedCell.x }},
              {{ selectedCell.y }})
            </h3>
            <button
              @click="showModal = false"
              class="text-gray-500 hover:text-gray-700"
            >
              <svg
                class="w-6 h-6"
                fill="none"
                stroke="currentColor"
                viewBox="0 0 24 24"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M6 18L18 6M6 6l12 12"
                />
              </svg>
            </button>
          </div>

          <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
            <!-- Soil information -->
            <div>
              <h4 class="font-semibold text-gray-800 mb-2">État du sol</h4>
              <div class="bg-gray-50 p-4 rounded-md">
                <div class="mb-3">
                  <p class="text-sm text-gray-600 mb-1">Taux d'humidité</p>
                  <div class="w-full bg-gray-200 rounded-full h-2.5">
                    <div
                      class="bg-blue-600 h-2.5 rounded-full"
                      :style="{ width: `${selectedCell.moisture * 100}%` }"
                    ></div>
                  </div>
                  <p class="text-xs text-right mt-1">
                    {{ Math.round(selectedCell.moisture * 100) }}%
                  </p>
                </div>
              </div>
            </div>

            <!-- Treatment device -->
            <div v-if="selectedCell.device">
              <h4 class="font-semibold text-gray-800 mb-2">
                Dispositif de traitement
              </h4>
              <div class="bg-gray-50 p-4 rounded-md">
                <p>
                  <span class="font-medium">Type:</span>
                  {{ deviceTypeNames[selectedCell.device.type] }}
                </p>
                <p>
                  <span class="font-medium">État:</span>
                  {{ selectedCell.device.isActive ? "Actif" : "Inactif" }}
                </p>
                <p v-if="selectedCell.device.schedule.length">
                  <span class="font-medium">Prochaine activation:</span> Jour
                  {{ selectedCell.device.schedule[0].day }}, Pas
                  {{ selectedCell.device.schedule[0].step }}
                </p>
                <button
                  class="mt-2 bg-blue-500 hover:bg-blue-600 text-white px-2 py-1 rounded text-sm"
                >
                  Configurer
                </button>
              </div>
            </div>

            <!-- Plants -->
            <div v-if="selectedCell.plants.length">
              <h4 class="font-semibold text-gray-800 mb-2">
                Plantes ({{ selectedCell.plants.length }})
              </h4>
              <div class="bg-gray-50 p-4 rounded-md max-h-40 overflow-y-auto">
                <div
                  v-for="(plant, index) in selectedCell.plants"
                  :key="index"
                  class="mb-2 pb-2 border-b border-gray-200 last:border-0"
                >
                  <p>
                    <span class="font-medium">Espèce:</span> {{ plant.species }}
                  </p>
                  <p>
                    <span class="font-medium">Âge:</span> {{ plant.age }} jours
                  </p>
                  <p v-if="plant.age >= plant.maturityAge">
                    <span class="font-medium">État:</span>
                    <span class="text-green-600 font-medium">Mature</span>
                  </p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>

</script>

