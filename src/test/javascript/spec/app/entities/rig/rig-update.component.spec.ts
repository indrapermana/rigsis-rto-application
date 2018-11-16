/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { RtoAppTestModule } from '../../../test.module';
import { RigUpdateComponent } from 'app/entities/rig/rig-update.component';
import { RigService } from 'app/entities/rig/rig.service';
import { Rig } from 'app/shared/model/rig.model';

describe('Component Tests', () => {
    describe('Rig Management Update Component', () => {
        let comp: RigUpdateComponent;
        let fixture: ComponentFixture<RigUpdateComponent>;
        let service: RigService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RtoAppTestModule],
                declarations: [RigUpdateComponent]
            })
                .overrideTemplate(RigUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RigUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RigService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Rig(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.rig = entity;
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
                    const entity = new Rig();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.rig = entity;
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
