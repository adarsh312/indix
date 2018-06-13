package skycard;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

class SkyCast {
	private static final Logger logger = Logger.getLogger(SkyCast.class.getName());

	private int buttonClicks;

	// init count of the clicks is 0
	SkyCast() {
		buttonClicks = 0;
	}

	/*
	 * method to find the number of clicks
	 * 
	 * @param range : limit of the channels
	 * 
	 * @param blocked : list of channels that are blocked
	 * 
	 * @param favorite :list of channels that to be watched
	 */
	public int clickCount(String range, String blocked, String favourite) {

		List<Integer> channelLimit = new ArrayList<>();
		
		// getting the limit from the string
		String[] rangeLimit = range.split(" ");
		int startNumber = Integer.parseInt(rangeLimit[0]);
		int endNumber = Integer.parseInt(rangeLimit[1]);

		checkLimit(startNumber, endNumber);

		//getting the list of blocked channels
		String[] blockedChannelList = blocked.split(" ");
		int blockedCount = Integer.parseInt(blockedChannelList[0]);

		// check for the valid number
		checkBlockedlimit(blockedCount);
		Set<Integer> blockedChannels = new HashSet<>();
		for (int i = 1; i <= blockedCount; i++) {
			blockedChannels.add(Integer.parseInt(blockedChannelList[i]));
		}

		// getting the list of channels to be favorite of the user 
		String[] listFavoriteChannels = favourite.split(" ");
		int favoriteChannelsCount = Integer.parseInt(listFavoriteChannels[0]);

		checkFavouriteLimit(favoriteChannelsCount);

		for (int i = 1; i <= favoriteChannelsCount; i++) {
			int channel = Integer.parseInt(listFavoriteChannels[i]);
			validateFavouriteChannel(startNumber, endNumber, blockedChannels, channel);
			channelLimit.add(channel);
		}
		
		//keep track of the back and previous to the back channel
		int previous = 0;
		int secondPrevious = 0;
		
		for (int i = 0; i < favoriteChannelsCount; i++) {

			int channelNumberSize = channelLimit.get(i).toString().length();
			// validation if the channel can be reached without using the digit buttons
			if (checkBlockedChannels(blockedChannels, previous, channelLimit.get(i))
					&& checkBoundaryCase(channelLimit.get(i), startNumber, endNumber, channelNumberSize, previous)
					&& checkGreaterOrSmallerChannel(channelLimit.get(i), channelNumberSize, previous, secondPrevious)) {

				buttonClicks += channelNumberSize;

			}
			secondPrevious = previous;
			previous = channelLimit.get(i);
		}

		return buttonClicks;
	}

	/*
	 * validate the limit to check the size
	 * 
	 * @param startnumber : the lower limit of the channel
	 * 
	 * @param endnumber : the higher limit of the channel
	 */
	private void checkLimit(int startNumber, int endNumber) {
		if (startNumber < 0 || startNumber > 10000 || endNumber < startNumber || endNumber > 10000) {
			logger.warning("range should be within 0 -10000 ");
			System.exit(0);
		}
	}

	/*
	 * validate the range of channels to be in the range
	 */
	public boolean checkrange(int current) {
		boolean result = false;
		if (current < 1 || current > 10000) {
			result = true;
		}
		return result;
	}

	/*
	 * check for the channels which are grater than or lesser than the value
	 * 
	 * @param current : the current channel that is in
	 * 
	 * @param size : the size of the String
	 * 
	 * @param previous : the previous channel that was watched
	 * 
	 * @param second previous : the channel that was second previously watched
	 */
	public boolean checkGreaterOrSmallerChannel(int current, int size, int previous, int secondPreviousNumber) {
		boolean result = true;
		if (current == previous) {
			result = false;
		} else if (current - 1 == previous || current + 1 == previous || current == secondPreviousNumber) {
			buttonClicks = buttonClicks + 1;
			result = false;
		} else {
			for (int j = 1; j < size; j++) {
				if (current - j == secondPreviousNumber || current + j == secondPreviousNumber) {
					buttonClicks = buttonClicks + j + 1;
					result = false;
				} else if (current - j == previous || current + j == previous) {
					buttonClicks = buttonClicks + j;
					result = false;
				}
			}
		}
		return result;
	}

	/*
	 * check the booundary case for the values given
	 * 
	 * @param current : the current channel which the user is in
	 * 
	 * @param startvalue : the channels start point
	 * 
	 * @param end value ; the channels last point
	 * 
	 * @param length: the length of the channel( the number of digits)
	 * 
	 * @param previous : the previously visited channel
	 */
	public boolean checkBoundaryCase(int current, int startvalue, int endValue, int length, int previous) {
		boolean result = true;
		int difference = endValue - startvalue;
		for (int ll = 0; ll < length; ll++) {
			int value = difference - ll;
			if (((previous + (value)) == current) || (previous - (value) == current)) {
				buttonClicks += ll + 1;
				result = false;
				break;
			}
		}
		return result;
	}
	/*
	 * check for the blocked channels
	 * 
	 * @param blocked : set containing the blocked channels
	 * 
	 * @param previous : the previous channel that it has
	 * 
	 * @param current : the current channel the user is in
	 */

	public boolean checkBlockedChannels(Set<Integer> blocked, int previous, int current) {
		boolean flag = true;
		if (previous != 0) {
			for (int start = 1; start < blocked.size() + 1; start++) {
				if (validateNextChannelForBlocked(blocked, previous, current, start)) {
					flag = false;
					buttonClicks++;
					return flag;
				}
			}
		}
		return flag;
	}

	/*
	 *  to validate the next channel to traverse to it
	 *  
	 */
	private boolean validateNextChannelForBlocked(Set<Integer> blocked, int previous, int current, int start) {
		return ((current > previous) && blocked.contains(previous + start))
				|| ((current < previous) && blocked.contains(previous - start))
						&& (((previous + (start + 1)) == current) || (previous - (start - 1)) == current);
	}

	/*
	 * validation for the favorite channel
	 * case 1: it should not be in the blocked channels
	 * case 2: it should not be in the out of the channel limit
	 */
	public void validateFavouriteChannel(int startNumber, int endNumber, Set<Integer> blocked, int sample) {
		if (checkrange(sample) || blocked.contains(sample) || sample < startNumber || sample > endNumber) {
			logger.warning("please check the channel number");
			System.exit(0);
		}
	}
	
	/*
	 * the check if the favorite channel is in the limit
	 * 
	 */
	public void checkFavouriteLimit(int allowedCount) {
		if (allowedCount < 1 || allowedCount > 50) {
			logger.warning("favourite channel out of range");
			System.exit(0);
		}
	}

	/*
	 * check if the blocked channels are in the limit
	 * 
	 */
	public void checkBlockedlimit(int blockedCount) {
		if (blockedCount > 40 && blockedCount < 0) {
			logger.warning("channel size more than 40");
			System.exit(0);
		}
	}

}
