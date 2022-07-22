package com.walagran.wwf

enum class LetterState {
    GREEN {
        override fun color(): Int {
            return R.color.green
        }
    },
    YELLOW {
        override fun color(): Int {
            return R.color.yellow
        }
    },
    BLACK {
        override fun color(): Int {
            return R.color.grey
        }
    },
    UNKNOWN {
        override fun color(): Int {
            return R.color.white
        }
    };

    abstract fun color(): Int
}