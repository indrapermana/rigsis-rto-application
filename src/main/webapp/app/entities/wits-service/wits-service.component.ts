import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IWitsService } from 'app/shared/model/wits-service.model';
import { Principal } from 'app/core';
import { WitsServiceService } from './wits-service.service';

@Component({
    selector: 'jhi-wits-service',
    templateUrl: './wits-service.component.html'
})
export class WitsServiceComponent implements OnInit, OnDestroy {
    witsServices: IWitsService[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private witsServiceService: WitsServiceService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.witsServiceService.query().subscribe(
            (res: HttpResponse<IWitsService[]>) => {
                this.witsServices = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInWitsServices();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IWitsService) {
        return item.id;
    }

    registerChangeInWitsServices() {
        this.eventSubscriber = this.eventManager.subscribe('witsServiceListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
