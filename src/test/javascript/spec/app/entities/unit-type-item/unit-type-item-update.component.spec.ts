/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { RtoAppTestModule } from '../../../test.module';
import { UnitTypeItemUpdateComponent } from 'app/entities/unit-type-item/unit-type-item-update.component';
import { UnitTypeItemService } from 'app/entities/unit-type-item/unit-type-item.service';
import { UnitTypeItem } from 'app/shared/model/unit-type-item.model';

describe('Component Tests', () => {
    describe('UnitTypeItem Management Update Component', () => {
        let comp: UnitTypeItemUpdateComponent;
        let fixture: ComponentFixture<UnitTypeItemUpdateComponent>;
        let service: UnitTypeItemService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RtoAppTestModule],
                declarations: [UnitTypeItemUpdateComponent]
            })
                .overrideTemplate(UnitTypeItemUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(UnitTypeItemUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UnitTypeItemService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new UnitTypeItem(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.unitTypeItem = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new UnitTypeItem();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.unitTypeItem = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
