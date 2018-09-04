var MyTabModel = Backbone.Collection.extend({
    url: 'data/onetimesetup.json'
})

class MyLayout extends allure.components.AppLayout {

    initialize() {
        this.model = new MyTabModel();
    }

    loadData() {
        return this.model.fetch();
    }

    getContentView() {
        return new MyView({items: this.model.models});
    }
}

const template = function (data) {
    html = '<h3 class="pane__title">Setup</h3>';
//    html += `<table><tr><th>Team</th><th>Jenkins Job</th><th>Comments</th></tr><tr><td id="team">Team-1</td><td><a href="${performanceJobs.team1}">Performance job</a></td><td>Job with Wealth performance tests</td></tr><tr><td id="team2">Team-2</td><td><a href="${performanceJobs.team2}">Performance job</a></td><td>Job with performance tests</td></tr></table>`
    for (var item of data.items) {
//        html += '<p>' + item.attributes.name + ' says: ' + item.attributes.sounds + '</p>';
           html += '<p>' + item '</p>';
    }
    return html;
}

var MyView = Backbone.Marionette.View.extend({
    template: template,

    render: function () {
        this.$el.html(this.template(this.options));
        return this;
    }
})

allure.api.addTab('mytab', {
    title: 'My tab', icon: 'fa fa-trophy',
    route: 'mytab',
    onEnter: (function () {
        return new MyLayout()
    })
});

//class MyWidget extends Backbone.Marionette.View {
//
//    template(data) {
//            return widgetTemplate(data)
//    }
//
//    serializeData() {
//        return {
//            items: this.model.get('items'),
//        }
//    }
//}
//
//allure.api.addWidget('mywidget', MyWidget);