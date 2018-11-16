import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IUnitTypeItem } from 'app/shared/model/unit-type-item.model';
import { Principal } from 'app/core';
import { UnitTypeItemService } from './unit-type-item.service';

@Component({
    selector: 'jhi-unit-type-item',
    templateUrl: './unit-type-item.component.html'
})
export class UnitTypeItemComponent implements OnInit, OnDestroy {
    unitTypeItems: IUnitTypeItem[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private unitTypeItemService: UnitTypeItemService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.unitTypeItemService.query().subscribe(
            (res: HttpResponse<IUnitTypeItem[]>) => {
                this.unitTypeItems = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInUnitTypeItems();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IUnitTypeItem) {
        return item.id;
    }

    registerChangeInUnitTypeItems() {
        this.eventSubscriber = this.eventManager.subscribe('unitTypeItemListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
