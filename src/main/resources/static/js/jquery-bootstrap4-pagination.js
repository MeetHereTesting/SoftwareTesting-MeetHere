+(function($){
    if (typeof $ === 'undefined') {
        throw Error('Pagination requires jQuery.');
    }

    var Pagination = function(ele, opt) {
        this.$element = ele,
            this.defaults = {
                pageSize: 5,
                page: 1,
                totalRows: 10,
                previous: '上一页',
                next: '下一页',
                pageLength: 5,
                onClickPage: null,
                debug: false
            }
        this.options = $.extend({}, this.defaults, opt)
        if(this.options.debug){
            console.log("options", this.options);
        }
    }

    Pagination.prototype = {
        _init: function() {
            this._checkData();
            this._render();
            this._bind();
            return this;
        },
        _caculateTotalPages: function(){
            return Math.ceil(this.options.totalRows / this.options.pageSize);
        },
        _bind: function(){
            var _this = this;
            var totalPages = this._caculateTotalPages();
            var page = this.options.page;
            var first = this.$element.find('.page-item').first();
            var last = this.$element.find('.page-item').last();
            this.$element.find('.page-item').bind('page', this.options.onClickPage);
            first.data('page') == page && first.addClass('disabled');
            last.data('page') == page && last.addClass('disabled');
            this.$element.find(".page-item").on("click", function () {
                if (!$(this).hasClass('active') && !$(this).hasClass('disabled')) {
                    $(this).trigger('page', $(this).data('page'));
                }
            });
        },
        _render: function(){
            var pageLength = this.options.pageLength;
            var page = this.options.page;
            var totalPages = this._caculateTotalPages();
            var active, start, end;
            var step = Math.floor(pageLength / 2);
            if(page - step < 1){
                start = 1
                end = totalPages > pageLength ? start + pageLength - 1 : totalPages;
            } else if(page + step > totalPages){
                start = totalPages > pageLength ? totalPages - pageLength + 1 : 1;
                end = totalPages;
            } else {
                start = page - step;
                end = start + pageLength - 1;
            }
            var str = '<ul class="pagination">';
            str += '<li class="page-item" data-page="' + this._previousPage() + '"><a class="page-link" href="#">' + this.options.previous + '</a></li>';
            for(start; start <= end; start++) {
                if(start == this.options.page)
                    active = ' active';
                else
                    active = '';
                str += '<li class="page-item' + active + '" data-page="' + start + '"><a class="page-link" href="#">' + start + '</a></li>';
            }
            str += '<li class="page-item" data-page="' + this._nextPage() + '"><a class="page-link" href="#">' + this.options.next + '</a></li>';
            str += '</ul>';
            this.$element.append(str);
            return this;
        },
        _checkData: function(){
            var totalPages = this._caculateTotalPages();
            var page = this.options.page;
            var pageLength = this.options.pageLength;
            if(totalPages < 0)
                throw Error('total pages is less than zero');
            if(page > totalPages)
                throw Error("page is beyond range");
            if(page <= 0)
                throw Error("page is less than or equal to zero");
            if(pageLength < 0)
                throw Error("page length should great than zero");
        },
        _previousPage: function(){
            return this.options.page == 1 ? 1 : parseInt(this.options.page) - 1;
        },
        _nextPage: function(){
            return this.options.page == this._caculateTotalPages() ? this._caculateTotalPages() : parseInt(this.options.page) + 1;
        }
    }

    $.fn.pagination = function(options) {
        var pagination = new Pagination(this, options);
        return pagination._init();
    }
})(jQuery)