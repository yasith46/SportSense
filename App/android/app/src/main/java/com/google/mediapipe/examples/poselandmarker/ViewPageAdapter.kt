import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.mediapipe.examples.poselandmarker.Home
import com.google.mediapipe.examples.poselandmarker.Leaderboard
import com.google.mediapipe.examples.poselandmarker.Profile

class ViewPagerAdapter(fragmentActivity: FragmentActivity,  private val data: String?) : FragmentStateAdapter(fragmentActivity) {
    private val fragmentsList = listOf(
        Home.newInstance(data),
        Leaderboard.newInstance(data),
        Profile.newInstance(data)
    )

    override fun getItemCount(): Int = fragmentsList.size

    override fun createFragment(position: Int): Fragment = fragmentsList[position]

}
